import { Stack } from "@mui/material";
import MoneyDivideInfo from "./MoneyDivideInfo";
import MoneyDivideListItem from "./MoneyDivideListItem";
import BaseButton from "../../components/button/BaseButton";
import { moneyDivide } from "../../utils/apis/order";
import { useState, useEffect } from "react";
import useWebSocketStore from "../../stores/SocketStore";

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
        console.log("TossPayments 초기화 성공:", widget);
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

  const handlePayment = async () => {
    if (!paymentWidget || !ready) {
      console.error("TossPayments 객체가 준비되지 않았습니다.");
      return;
    }

    try {
      console.log("결제 요청 시작:", { money });
      await paymentWidget.requestPayment("카드", {
        amount: money,
        orderId: `order-${Date.now()}`, // 고유한 주문 ID
        orderName: `${orders.tableName}의 주문`,
        successUrl: `${window.location.origin}/success`,
        failUrl: `${window.location.origin}/fail`,
        customerKey,
      });
    } catch (error) {
      console.error("결제 요청 중 오류:", error);
      alert("결제 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
    }
  };

  const handleVaulesChange = ({ total, portion, money }) => {
    setTotal(total);
    setPortion(portion);
    setMoney(money);
  };

  useEffect(() => {
    const tableId = sessionStorage.getItem("tableId");
    if (client && client.connected && tableId) {
      client.subscribe(`/sub/payment/${tableId}`, (message) => {
        try {
          const parsedBody = JSON.parse(message.body || message._body);
          console.log("수신된 메시지 내용:", parsedBody);
        } catch (error) {
          console.error("메시지 파싱 오류:", error);
        }
      });
    }
  }, [client]);

  return (
    <Stack>
      <MoneyDivideInfo
        initTotal={orders.userCnt}
        totalPrice={orders.price}
        onValuesChange={handleVaulesChange}
      />
      {conjoinedDishesArray.map((dish) => (
        <MoneyDivideListItem key={dish.key} dish={dish} />
      ))}
      <BaseButton onClick={handlePayment} disabled={!ready}>
        {money.toLocaleString()}원 결제하기
      </BaseButton>
    </Stack>
  );
};

export default MoneyDivideList;
