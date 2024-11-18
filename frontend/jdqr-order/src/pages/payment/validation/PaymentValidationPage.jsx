import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import useWebSocketStore from "../../../stores/SocketStore";
import { Box, Typography, Button, Stack } from "@mui/material";
import { colors } from "../../../constants/colors";
import LoadingSpinner from "../../../components/Spinner/LoadingSpinner";
import BaseButton from "../../../components/button/BaseButton";

export default function PaymentValidationPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { client, connect } = useWebSocketStore();
  const tableId = sessionStorage.getItem("tableId");
  const [isSubscribed, setIsSubscribed] = useState(false);

  useEffect(() => {
    if (!client) {
      connect();
    }

    if (client && client.connected && tableId) {
      const subscription = client.subscribe(
        `/sub/payment/${tableId}`,
        (message) => {
          console.log("WebSocket 메시지 수신");
          try {
            const parsedBody = JSON.parse(message.body || message._body);
            console.log("수신된 메시지 내용:", parsedBody);
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
    <Stack spacing={2} alignContent={"center"}>
      <LoadingSpinner
        message={
          <Stack>
            <Typography textAlign={"center"}>{`주문번호: ${searchParams.get(
              "orderId"
            )}`}</Typography>
            <Typography textAlign={"center"}>
              {`결제 금액 ${Number(
                searchParams.get("amount")
              ).toLocaleString()}원`}
              에 대한 결제 검증을 진행 중입니다.
            </Typography>
          </Stack>
        }
      />
    </Stack>
  );
}
