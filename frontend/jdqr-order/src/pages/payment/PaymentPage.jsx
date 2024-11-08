import BaseButton from "../../components/button/BaseButton";
import { Stack, Box } from "@mui/material";
import Header from "../../components/header/Header";
import PaymentList from "./PaymentList";

const PaymentPage = ({}) => {
  return (
    <Box>
      <Header title="결제하기" BackPage={true} />
      <PaymentList />
    </Box>
  );
};

export default PaymentPage;
