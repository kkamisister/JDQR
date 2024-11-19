import { Stack, Typography, Button, Box, Divider } from "@mui/material";
import { colors } from "../../constants/colors";
import DishItemCard from "../../components/card/DishItemCard";
import NumberSelector from "../../components/selector/NumberSelector";

const CartListItem = ({ title, dishes, onUpdateQuantity, onDeleteDish }) => {
  return (
    <Stack spacing={2}>
      <Typography
        sx={{
          fontWeight: 600,
          fontSize: "16px",
          p: "10px",
        }}
      >
        {title}
      </Typography>
      {dishes.map((dish, index) => (
        <Box key={index}>
          <DishItemCard
            dish={dish}
            hasImage={false}
            hasOption={true}
            onClose={() => onDeleteDish(dish)} // 삭제 요청
          >
            <Stack
              direction="row"
              spacing={1}
              sx={{ alignSelf: "end", px: 2, pb: 1 }}
            >
              <Button
                variant="disabled"
                sx={{
                  bgcolor: colors.background.box,
                  borderRadius: 2,
                  fontSize: "12px",
                  height: "25px",
                  p: 0,
                }}
              >
                옵션 변경
              </Button>
              <NumberSelector
                value={dish.quantity}
                onIncrease={() => onUpdateQuantity(dish, 1)} // 수량 증가 요청
                onDecrease={() => onUpdateQuantity(dish, -1)} // 수량 감소 요청
                sx={{ width: "70px" }}
              />
            </Stack>
          </DishItemCard>
          <Divider variant="middle" />
        </Box>
      ))}
    </Stack>
  );
};

export default CartListItem;
