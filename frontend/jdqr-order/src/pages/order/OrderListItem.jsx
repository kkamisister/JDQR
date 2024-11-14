import { Divider, Stack, Box } from "@mui/material";
import { colors } from "../../constants/colors";
import OrderedDishItem from "./OrderedDishItem";

export default function OrderListItem({ order, index }) {
  return (
    <Stack bgcolor={colors.background.white} p={2}>
      <Stack
        direction="row"
        sx={{
          fontWeight: 600,
          fontSize: 18,
          justifyContent: "space-between",
          color: colors.text.main,
        }}
      >
        <span>주문 {index + 1}</span>
        <span>{order.price.toLocaleString()}원</span>
      </Stack>
      <Divider sx={{ mt: 1, mb: 2 }} />
      {order.dishes.map((dish, index) => (
        <Box key={index}>
          <OrderedDishItem dish={dish} />
          {index !== order.dishes.length - 1 && <Divider sx={{ my: 2 }} />}
        </Box>
      ))}
    </Stack>
  );
}
