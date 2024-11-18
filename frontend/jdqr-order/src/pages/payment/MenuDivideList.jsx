import { Stack, FormControlLabel, Checkbox, Typography } from "@mui/material";
import { useState, useEffect } from "react";
import { colors } from "../../constants/colors";
import MenuDivideListItem from "./MenuDivideListItem";
import QuantitySelectDialog from "../../components/dialog/QuantitySelectDialog";
import useWebSocketStore from "../../stores/SocketStore";
import BaseButton from "../../components/button/BaseButton";
import { menuDivide } from "../../utils/apis/order";

const clientKey = "test_ck_d46qopOB89JwBn4D9R7d3ZmM75y0"; // TossPayments 클라이언트 키
const userId = sessionStorage.getItem("userId");
const customerKey =
  `K2.${userId}`.length > 250 ? `K2.${userId}`.slice(0, 250) : `K2.${userId}`;

export default function MenuDivideList({ orders }) {
  const allDishes = orders.orders.flatMap((order) => order.dishes);
  const [showOnlyMine, setShowOnlyMine] = useState(false);
  const [selectedItems, setSelectedItems] = useState([]); // 선택된 아이템 관리
  const [dialogOpen, setDialogOpen] = useState(false);
  const [currentDish, setCurrentDish] = useState(null); // 현재 선택된 메뉴
  const { client, connect } = useWebSocketStore();
  const [ready, setReady] = useState(false);
  const [paymentWidget, setPaymentWidget] = useState(null);

  const filteredDishes = showOnlyMine
    ? allDishes.filter((dish) => dish.userId === userId)
    : allDishes;

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
        // console.log("TossPayments 초기화 성공:", widget);
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

  const handleDishClick = (dish) => {
    if (dish.quantity > 1) {
      // 수량 선택 다이얼로그 열기
      setCurrentDish(dish);
      setDialogOpen(true);
    } else {
      // 수량 1일 때 바로 추가/삭제
      toggleSelection(dish.orderItemId, 1);
    }
  };

  const toggleSelection = (orderItemId, quantity) => {
    setSelectedItems((prev) => {
      const exists = prev.find((item) => item.orderItemId === orderItemId);
      if (exists) {
        // 이미 선택된 경우 제거
        return prev.filter((item) => item.orderItemId !== orderItemId);
      } else {
        // 새로 추가
        return [...prev, { orderItemId, quantity }];
      }
    });
  };

  const handleDialogConfirm = (quantity) => {
    if (currentDish) {
      toggleSelection(currentDish.orderItemId, quantity);
    }
    setDialogOpen(false);
    setCurrentDish(null);
  };

  const handlePayment = async () => {
    if (!paymentWidget || !ready) {
      console.error("TossPayments 객체가 준비되지 않았습니다.");
      return;
    }

    try {
      const response = await menuDivide({
        orderItemInfos: selectedItems,
      });

      const { tossOrderId, amount } = response;
      console.log("menuDivide API 응답:", response);

      await paymentWidget.requestPayment("카드", {
        amount, // menuDivide로 받은 결제 금액
        orderId: tossOrderId, // menuDivide로 받은 tossOrderId
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

  return (
    <Stack>
      <FormControlLabel
        control={
          <Checkbox
            checked={showOnlyMine}
            onChange={(e) => setShowOnlyMine(e.target.checked)}
            sx={{
              color: colors.main.primary500,
              "&.Mui-checked": {
                color: colors.main.primary500,
              },
            }}
          />
        }
        label="내가 시킨 메뉴만 보기"
        sx={{ alignSelf: "flex-end", mb: 2 }}
      />

      {filteredDishes.map((dish) => {
        const key = dish.orderItemId;
        const description = dish.options
          ?.map((option) => option.choiceName)
          .join(", ") || <>&nbsp;</>;
        const isSelected = selectedItems.some(
          (item) => item.orderItemId === key
        );
        return (
          <MenuDivideListItem
            key={key}
            dish={dish}
            description={description}
            isSelected={isSelected} // 선택 여부 전달
            onClick={() => handleDishClick(dish)} // 클릭 핸들러
          />
        );
      })}

      <QuantitySelectDialog
        open={dialogOpen}
        onClose={() => setDialogOpen(false)}
        maxQuantity={currentDish?.quantity || 1}
        onSelectQuantity={handleDialogConfirm}
      />
      <BaseButton
        onClick={handlePayment}
        disabled={!ready || selectedItems.length === 0}
      >
        선택한 메뉴 결제하기
      </BaseButton>
    </Stack>
  );
}
