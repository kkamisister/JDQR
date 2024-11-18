import React, { useState } from "react";
import { Modal, Box, Typography, Button, Backdrop } from "@mui/material";

const PaymentModal = ({ open, onClose, total, money, onPay }) => {
  const remainingAmount = total - money; // 남은 결제 금액 계산
  const perPerson = remainingAmount > 0 ? Math.ceil(remainingAmount / 2) : 0; // 1인당 결제 금액 예제 계산

  return (
    <Modal
      open={open}
      onClose={(event, reason) => {
        if (reason !== "backdropClick") return; // 모달 외부 클릭 방지
      }}
      closeAfterTransition
      BackdropComponent={Backdrop}
      BackdropProps={{
        timeout: 500,
        style: { backgroundColor: "rgba(0, 0, 0, 0.7)" }, // 배경 어둡게
      }}
    >
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 400,
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
          textAlign: "center",
        }}
      >
        <Typography variant="h6" gutterBottom>
          결제 내역
        </Typography>
        <Typography variant="body1" gutterBottom>
          총액: {total.toLocaleString()}원 중 {money.toLocaleString()}원
          결제되었습니다.
        </Typography>
        <Typography variant="body1" gutterBottom>
          남은 결제 금액:{" "}
          {remainingAmount > 0 ? remainingAmount.toLocaleString() : 0}원
        </Typography>
        <Typography variant="body1" gutterBottom>
          1인: {perPerson.toLocaleString()}원
        </Typography>
        <Button
          variant="contained"
          color="primary"
          fullWidth
          onClick={onPay}
          disabled={remainingAmount <= 0} // 남은 금액이 0원 이하일 경우 버튼 비활성화
          sx={{ mt: 2 }}
        >
          나도 결제하기
        </Button>
      </Box>
    </Modal>
  );
};

export default function App() {
  const [open, setOpen] = useState(true);

  const handlePay = () => {
    alert("결제 완료!"); // 결제 로직 구현
    setOpen(false); // 모달 닫기
  };

  return (
    <div>
      <PaymentModal
        open={open}
        onClose={() => setOpen(false)}
        total={100000} // 총 결제 금액 예제
        money={50000} // 이미 결제된 금액 예제
        onPay={handlePay}
      />
    </div>
  );
}
