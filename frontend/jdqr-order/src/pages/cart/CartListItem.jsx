import { useEffect, useState } from "react";
import { Stack, Typography, Button, Box, Divider } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { colors } from "../../constants/colors";
import DishItemCard from "../../components/card/DishItemCard";
import { useNavigate } from "react-router-dom";
import NumberSelector from "../../components/selector/NumberSelector";
import { useSnackbar } from "notistack";
import useWebSocketStore from "../../stores/SocketStore";

const CartListItem = ({ title, dishes }) => {
  const { client, connect } = useWebSocketStore();

  const updateQuantity = (dish, change) => {
    if (client && client.connected) {
      const postData = {
        userId: dish.userId,
        dishId: dish.dishId,
        choiceIds: dish.choiceIds,
        quantity: change,
      };
      client.send("/pub/cart/add", {}, JSON.stringify(postData));
    } else {
      console.error(" 수량 변경 실패");
    }
  };

  const deleteDish = (dish) => {
    if (client && client.connected) {
      const postData = {
        userId: dish.userId,
        dishId: dish.dishId,
        choiceIds: dish.choiceIds,
      };
      client.send("/pub/cart/delete", {}, JSON.stringify(postData));
      console.log("삭제 요청은 보냄");
    } else {
      console.error("삭제하려는데 연결이 안되네,,,,");
    }
  };
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
      {dishes.length > 0 &&
        dishes.map((dish, index) => (
          <Box key={index}>
            <DishItemCard
              dish={dish}
              hasImage={false}
              hasOption={true}
              onClose={() => deleteDish(dish)}
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
                  onIncrease={() => updateQuantity(dish, 1)}
                  onDecrease={() => updateQuantity(dish, -1)}
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
