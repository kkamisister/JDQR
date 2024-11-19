import { Stack, Typography, Divider, Box } from "@mui/material";
import DishItemCard from "../../components/card/DishItemCard";
import { colors } from "../../constants/colors";

export default function MenuDivideListItem({
  dish,
  description,
  isSelected,
  onClick,
}) {
  return (
    <Stack mb={1}>
      <DishItemCard
        dish={dish}
        hasImage={false}
        onClick={onClick}
        isSelected={isSelected}
      >
        <Stack>
          <Typography fontSize={12} color={colors.text.sub2}>
            {description}
          </Typography>

          <Stack direction="row" justifyContent="space-between">
            <Typography fontSize={12}>
              총 수량 <span style={{ fontSize: 16 }}>{dish.quantity}</span>개
            </Typography>
            <Typography fontSize={14}>
              {(dish.quantity * dish.price).toLocaleString()}원
            </Typography>
          </Stack>
          <Stack direction="row" justifyContent="space-between">
            <Typography fontSize={12}>
              잔여 수량{" "}
              <span style={{ fontSize: 16 }}>{dish.restQuantity}</span>개
            </Typography>
            <Typography fontSize={14}>
              <span style={{ fontSize: 12 }}>미결제액 </span>
              {(dish.restQuantity * dish.price).toLocaleString()}원
            </Typography>
          </Stack>
        </Stack>
      </DishItemCard>
      <Divider variant="middle" />
    </Stack>
  );
}
