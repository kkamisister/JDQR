import { Stack } from "@mui/material";
import MoneyDivideInfo from "./MoneyDivideInfo";
import MoneyDivideListItem from "./MoneyDivideListItem";
import BaseButton from "../../components/button/BaseButton";
import { moneyDivide } from "../../utils/apis/order";
import { useState, useEffect } from "react";
import useWebSocketStore from "../../stores/SocketStore";
import { useQuery } from "@tanstack/react-query";
import { fetchPaymentList } from "../../utils/apis/order";
import PaymentModal from "../../components/modal/PaymentModal";

const clientKey = "test_ck_d46qopOB89JwBn4D9R7d3ZmM75y0"; // TossPayments 클라이언트 키
const userId = sessionStorage.getItem("userId") || ""; // 사용자 ID 가져오기
const customerKey =
  `K2.${userId}`.length > 250 ? `K2.${userId}`.slice(0, 250) : `K2.${userId}`;

const MoneyDivideList = ({ orders }) => {
  const dishes = orders.orders.flatMap((order) => order.dishes);
  const [total, setTotal] = useState(orders.userCnt);
  const [portion, setPortion] = useState(1);
  const [money, setMoney] = useState(0);
  const { client, connect } = useWebSocketStore();
  const [ready, setReady] = useState(false);
  const [paymentWidget, setPaymentWidget] = useState(null);

  // 추가된 상태: 모달 표시 상태
  const [showModal, setShowModal] = useState(false);

  // UseQuery로 paymentList 가져오기
  const {
    data: paymentList,
    isLoading,
    isError,
    refetch,
  } = useQuery({
    queryKey: ["paymentList"],
    queryFn: fetchPaymentList,
    refetchOnMount: true,
    refetchOnWindowFocus: false,
  });

  const conjoinedDishes = dishes.reduce((acc, dish) => {
    const key = `${dish.dishId}-${dish.options
      ?.map((option) => `${option.optionId}-${option.choiceId}`)
      .sort()
      .join("_")}`;

    if (acc[key]) {
      acc[key].totalPrice += dish.price * dish.quantity;
      acc[key].quantity += dish.quantity;
    } else {
      acc[key] = {
        key,
        dishId: dish.dishId,
        dishName: dish.dishName,
        price: dish.price,
        totalPrice: dish.price * dish.quantity,
        quantity: dish.quantity,
        dishCategoryId: dish.dishCategoryId,
        dishCategoryName: dish.dishCategoryName,
        options: [...dish.options],
      };
    }

    return acc;
  }, {});

  const conjoinedDishesArray = Object.values(conjoinedDishes);

  useEffect(() => {
    if (!client) {
      connect();
    }

    const script = document.createElement("script");
    script.src = "https://js.tosspayments.com/v1/payment";
    script.async = true;
    script.onload = () => {
      if (window.TossPayments) {
        const widget = window.TossPayments(clientKey);
        setPaymentWidget(widget);
        setReady(true);
      } else {
        console.error("TossPayments 객체를 로드하지 못했습니다.");
      }
    };
    script.onerror = () => {
      console.error("TossPayments 스크립트 로드 실패");
    };
    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, [client, connect]);

  const moneyPay = async ({ peopleNum, serveNum }) => {
    try {
      const response = await moneyDivide({
        peopleNum,
        serveNum,
      });

      const { tossOrderId, amount } = response;
      return { tossOrderId, amount };
    } catch (error) {
      console.error("moneyPay 요청 중 에러 발생:", error);
      throw error;
    }
  };

  // WebSocket 메시지를 구독하고 paymentList를 갱신
  useEffect(() => {
    if (client && client.connected) {
      client.subscribe("/sub/payment", () => {
        refetch(); // 메시지를 받을 때마다 데이터를 다시 가져옴
        if (paymentList?.restPrice > 0) {
          setShowModal(true); // 잔여 금액이 있을 경우 모달 표시
        }
      });
    }
  }, [client, refetch, paymentList]);

  const handleVaulesChange = ({ total, portion, money }) => {
    setTotal(total);
    setPortion(portion);
    setMoney(money);
  };

  return (
    <>
      {/* 잔여 금액 모달 */}
      {showModal && paymentList?.restPrice > 0 && (
        <PaymentModal
          restPrice={paymentList.restPrice}
          peopleNum={paymentList.peopleNum} // 결제 가능한 인분 수 전달
          onPayment={moneyPay} // moneyPay 전달
          paymentWidget={paymentWidget} // TossPayments 객체 전달
          customerKey={customerKey}
        />
      )}
      <Stack>
        <MoneyDivideInfo
          initTotal={orders.userCnt}
          totalPrice={orders.price}
          onValuesChange={handleVaulesChange}
        />
        {conjoinedDishesArray.map((dish) => (
          <MoneyDivideListItem key={dish.key} dish={dish} />
        ))}
        <BaseButton onClick={() => setShowModal(true)} disabled={!ready}>
          {money.toLocaleString()}원 결제하기
        </BaseButton>
      </Stack>
    </>
  );
};

export default MoneyDivideList;
