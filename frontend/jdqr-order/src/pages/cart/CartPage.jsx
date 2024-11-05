import Header from "../../components/header/Header";
import { Box, Stack } from "@mui/material";
import { colors } from "../../constants/colors";
import CartList from "./CartList";

export default function CartPage() {
  return (
    <Box sx={{ bgcolor: colors.background.box, height: "100vh" }}>
      <Header title="장바구니" BackPage={true} />
      <CartList />
    </Box>
  );
}
