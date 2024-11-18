import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import useWebSocketStore from "../../../stores/SocketStore";
import { Box, Typography, Button } from "@mui/material";
import { colors } from "../../../constants/colors";

export function FailurePage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { client, connect } = useWebSocketStore();
  const [timeLeft, setTimeLeft] = useState(3); // 남은 시간 상태 관리
  const tableId = sessionStorage.getItem("tableId");

  // useEffect(() => {
  //   if (!client) {
  //     connect();
  //   }
  // }, [client, connect]);

  // useEffect(() => {
  //   if (client && client.connected) {
  //     const message = {
  //       header: {
  //         tossOrderId: searchParams.get("orderId"),
  //         tableId,
  //         status: "success",
  //       },
  //       data: {
  //         paymentKey: searchParams.get("paymentKey"),
  //         amount: searchParams.get("amount"),
  //       },
  //     };

  //     // WebSocket 메시지 전송
  //     try {
  //       client.send(
  //         "/pub/payment",
  //         message.header,
  //         JSON.stringify(message.data) // 메시지를 JSON 문자열로 직렬화
  //       );
  //       console.log("메시지 전송:", message);
  //     } catch (error) {
  //       console.error("결제 확인 중 오류:", error);
  //     }
  //   }
  // }, [client, searchParams, tableId]);

  // 3초 카운트다운 및 리다이렉트 로직
  useEffect(() => {
    const timer = setInterval(() => {
      setTimeLeft((prev) => {
        if (prev === 1) {
          navigate("/payment"); // 3초 후 리다이렉트
          clearInterval(timer);
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(timer); // 컴포넌트 언마운트 시 타이머 정리
  }, [navigate]);

  // 확인 버튼 클릭 핸들러
  const handleImmediateRedirect = () => {
    navigate("/payment");
  };

  return (
    <Box className="result wrapper" textAlign="center" p={4}>
      <Box className="box_section" mb={3}>
        <Typography variant="h4" gutterBottom>
          결제 실패
        </Typography>
        <Typography>{`주문번호: ${searchParams.get("orderId")}`}</Typography>
        <Typography>{`결제 금액: ${Number(
          searchParams.get("amount")
        ).toLocaleString()}원`}</Typography>
      </Box>
      <Typography variant="body1" color="textSecondary" mb={2}>
        {`3초 후 주문 페이지로 돌아갑니다. (${timeLeft}초)`}
      </Typography>
      <Button
        variant="contained"
        color={colors.main.primary500}
        onClick={handleImmediateRedirect}
      >
        확인
      </Button>
    </Box>
  );
}
