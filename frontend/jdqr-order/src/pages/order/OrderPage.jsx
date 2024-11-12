import Header from "../../components/header/Header";
import { Stack } from "@mui/material";
import OrderList from "./OrderList";

const OrderPage = () => {
  return (
    <Stack>
      <Header title={"장바구니"} BackPage={true} />
      <OrderList />
    </Stack>
  );
};

export default OrderPage;
