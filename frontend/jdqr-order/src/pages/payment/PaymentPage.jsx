import BaseButton from "../../components/button/BaseButton";
import { Box } from "@mui/material";

const PaymentPage = () => {
  return (
    <Box>
      <BaseButton count={20}>
        {(1489032).toLocaleString()}원 결제하기
      </BaseButton>
    </Box>
  );
};

export default PaymentPage;
