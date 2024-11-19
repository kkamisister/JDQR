import Header from "../../components/header/Header";
import { Stack } from "@mui/material";
import OrderList from "./OrderList";
import { colors } from "../../constants/colors";
import Footer from "../../components/footer/Footer";

const OrderPage = () => {
  return (
    <Stack>
      <Header title={"장바구니"} BackPage={true} />
      <OrderList />
      <Footer />
    </Stack>
  );
};

export default OrderPage;
