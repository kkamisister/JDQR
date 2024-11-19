import React, { useState } from "react";
import { Modal, Box, Typography, Button, TextField } from "@mui/material";
import { colors } from "../../constants/colors";

const PaymentModal = ({
  restPrice,
  peopleNum,
  onPayment,
  paymentWidget,
  customerKey,
}) => {
  const [serveNum, setServeNum] = useState(1); // 초기값 1인분

  const handlePaymentClick = async () => {
    if (!paymentWidget) {
      console.error("TossPayments 객체가 준비되지 않았습니다.");
      alert("결제 시스템이 초기화되지 않았습니다. 잠시 후 다시 시도해 주세요.");
      return;
    }

    if (serveNum > 0 && serveNum <= peopleNum) {
      try {
        // moneyPay 호출
        const { tossOrderId, amount } = await onPayment({
          peopleNum,
          serveNum,
        });

        // SDK 결제 요청
        await paymentWidget.requestPayment("카드", {
          amount,
          orderId: tossOrderId,
          orderName: `${serveNum}인분 결제`,
          successUrl: `${window.location.origin}/success`,
          failUrl: `${window.location.origin}/fail`,
          customerKey,
        });
        console.log(`결제 성공: OrderID: ${tossOrderId}, Amount: ${amount}`);
      } catch (error) {
        console.error("결제 요청 중 오류:", error);
        alert("결제 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
      }
    } else {
      alert("유효한 인분을 입력해주세요.");
    }
  };

  return (
    <Modal open={true} aria-labelledby="payment-modal-title">
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 300,
          bgcolor: colors.background.white,
          borderRadius: "10px",
          boxShadow: 24,
          p: 4,
          textAlign: "center",
        }}
      >
        <Typography id="payment-modal-title" fontSize={16}>
          잔여 금액: {restPrice.toLocaleString()}원
        </Typography>
        <Typography fontSize={14}>
          {peopleNum}인분 중에{" "}
          <TextField
            type="number"
            value={serveNum}
            onChange={(e) =>
              setServeNum(
                Math.min(Math.max(Number(e.target.value), 1), peopleNum)
              )
            }
            inputProps={{ min: 1, max: peopleNum }}
            sx={{ width: 60 }}
          />{" "}
          인분
        </Typography>
        <Button
          variant="contained"
          sx={{
            mt: 2,
            backgroundColor: colors.main.primary500,
            "&:hover": { backgroundColor: colors.main.primary700 },
          }}
          onClick={handlePaymentClick}
        >
          결제하기
        </Button>
      </Box>
    </Modal>
  );
};

export default PaymentModal;
