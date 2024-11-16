import { Stack, Typography, Box } from "@mui/material";
import DishItemText from "./DishItemText";
import { colors } from "../../constants/colors";

export default function OrderedDishItem({ dish }) {
  return (
    <Stack>
      <DishItemText sx={{ color: colors.text.main }}>
        <span>{dish.dishName}</span>
        <span>{dish.totalPrice.toLocaleString()}원</span>
      </DishItemText>
      <DishItemText>
        <span>기본</span>
        <span>{dish.price.toLocaleString()}원</span>
      </DishItemText>
      {dish.options?.map((option) => (
        <DishItemText key={option.optionId}>
          <span>{option.choiceName}</span>
          <span>{option.price.toLocaleString()}원</span>
        </DishItemText>
      ))}
      <DishItemText>
        <span>수량</span>
        <span>{`x${dish.quantity}개`}</span>
      </DishItemText>
      <DishItemText sx={{ color: colors.text.main }}>
        <span>합계</span>
        <span>{(dish.totalPrice * dish.quantity).toLocaleString()}원</span>
      </DishItemText>
    </Stack>
  );
}
