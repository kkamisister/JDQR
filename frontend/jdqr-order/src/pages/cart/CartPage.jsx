import Header from "../../components/header/Header";
import { Box } from "@mui/material";

export default function CartPage() {
  return (
    <Box>
      <Header title="장바구니" BackPage={true} />
    </Box>
  );
}
