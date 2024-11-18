import { Stack, Typography, Divider } from "@mui/material";
import DishItemCard from "../../components/card/DishItemCard";
import { colors } from "../../constants/colors";

export default function MenuDivideListItem({ dish, description }) {
  // console.log(dish);
  return (
    <Stack>
      <DishItemCard dish={dish} hasImage={false}>
        <Stack>
          <Typography fontSize={12} color={colors.text.sub2}>
            {description}
          </Typography>

          <Stack direction="row" justifyContent="space-between">
            <Typography fontSize={12}>
              총 수량 <span style={{ fontSize: 16 }}>{dish.quantity}</span>개
            </Typography>
            <Typography fontSize={14}>
              총 {(dish.quantity * dish.price).toLocaleString()}원
            </Typography>
          </Stack>
        </Stack>
      </DishItemCard>
      <Divider variant="middle" />
    </Stack>
  );
}
