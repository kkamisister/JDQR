import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import useWebSocketStore from "../../../stores/SocketStore";
import { Box, Typography, Button, Stack, keyframes } from "@mui/material";
import { colors } from "../../../constants/colors";
import LockClockIcon from "@mui/icons-material/LockClock";

export default function PaymentValidationPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { client, connect } = useWebSocketStore();
  const tableId = sessionStorage.getItem("tableId");
  const [isSubscribed, setIsSubscribed] = useState(false);
  const shakeAnimation = keyframes`
  0% {
    transform: rotate(0deg);
  }
  25% {
    transform: rotate(-10deg);
  }
  50% {
    transform: rotate(10deg);
  }
  75% {
    transform: rotate(-10deg);
  }
  100% {
    transform: rotate(0deg);
  }
`;

  useEffect(() => {
    if (!client) {
      connect();
    }

    if (client && client.connected && tableId) {
      const subscription = client.subscribe(
        `/sub/payment/${tableId}`,
        (message) => {
          try {
            const parsedBody = JSON.parse(message.body || message._body);
            console.log("수신된 메시지 내용:", parsedBody.status);
            if (parsedBody.status === "WHOLE_PAYMENT_SUCCESS") {
              navigate("/celebration");
            } else if (parsedBody.status === "PAYMENT_SUCCESS") {
              navigate("/payment");
            }
          } catch (error) {
            console.error("메시지 파싱 오류:", error);
          }
        }
      );

      setIsSubscribed(true); // 구독 완료
      console.log("구독 완료");

      // 컴포넌트 언마운트 시 구독 해제
      return () => {
        subscription.unsubscribe();
        setIsSubscribed(false); // 구독 해제
        console.log("구독 해제");
      };
    }
  }, [client, connect, tableId]);

  useEffect(() => {
    if (client && client.connected && isSubscribed) {
      const message = {
        header: {
          tossOrderId: searchParams.get("orderId"),
          tableId,
          status: "success",
        },
        data: {
          paymentKey: searchParams.get("paymentKey"),
          amount: searchParams.get("amount"),
        },
      };

      // WebSocket 메시지 전송
      try {
        client.send(
          "/pub/payment",
          message.header,
          JSON.stringify(message.data) // 메시지를 JSON 문자열로 직렬화
        );
        console.log("메시지 전송:", message);
      } catch (error) {
        console.error("결제 확인 중 오류:", error);
      }
    }
  }, [client, searchParams, tableId, isSubscribed]);

  return (
    <Stack
      height={"80vh"}
      spacing={2}
      justifyContent={"center"}
      alignItems={"center"}
      textAlign={"center"}
    >
      <LockClockIcon
        sx={{
          fontSize: 150,
          color: colors.main.primary500,
          animation: `${shakeAnimation} 1s ease-in-out infinite`, // 흔들림 애니메이션 적용
        }}
      />

      <Typography textAlign={"center"}>{`주문번호: ${searchParams.get(
        "orderId"
      )}`}</Typography>
      <Typography textAlign={"center"}>
        {`결제 금액 ${Number(searchParams.get("amount")).toLocaleString()}원`}에
        대한 결제 검증을 진행 중입니다.
      </Typography>
    </Stack>
  );
}
