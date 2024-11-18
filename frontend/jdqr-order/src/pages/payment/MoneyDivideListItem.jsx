import { Divider, Stack, Typography } from "@mui/material";
import DishItemCard from "../../components/card/DishItemCard";

export default function MoneyDivideListItem({ dish }) {
  const optionDescription = dish.options
    ?.map((option) => option.choiceName)
    .join(", ");
  return (
    <>
      <DishItemCard
        dish={{ ...dish, description: optionDescription || "" }}
        hasImage={false}
      >
        <Stack direction="row" justifyContent="space-between">
          <Typography fontSize={12}>
            총 수량 <span style={{ fontSize: 16 }}>{dish.quantity}</span>개
          </Typography>
          <Typography fontSize={14}>
            총 {dish.totalPrice.toLocaleString()}원
          </Typography>
        </Stack>
      </DishItemCard>
      <Divider variant="middle" />
    </>
  );
}
