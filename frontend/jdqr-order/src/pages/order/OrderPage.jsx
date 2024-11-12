import Header from "../../components/header/Header";
import { Stack } from "@mui/material";

const OrderPage = () => {
  return (
    <Stack>
      <Header title={"장바구니"} BackPage={true} />
    </Stack>
  );
};

export default OrderPage;
