import { Stack, Box, Divider, Typography } from "@mui/material";
import MoneyDivideInfo from "./MoneyDivideInfo";
import DishItemCard from "../../components/card/DishItemCard";
import { colors } from "../../constants/colors";

const MoneyDivideList = ({ orders }) => {
  const dishes = orders.orders.flatMap((order) => order.dishes);

  const conjoinedDishes = dishes.reduce((acc, dish) => {
    const key = `${dish.dishId}-${dish.options
      ?.map((option) => `${option.optionId}-${option.choiceId}`) // optionId-choiceId 형태로 변환
      .sort() // 옵션을 정렬
      .join("_")}`; // 정렬된 옵션을 병합

    if (acc[key]) {
      acc[key].totalPrice += dish.price * dish.quantity;
      acc[key].quantity += dish.quantity;
    } else {
      acc[key] = {
        dishId: dish.dishId,
        dishName: dish.dishName,
        price: dish.price,
        totalPrice: dish.price * dish.quantity,
        quantity: dish.quantity,
        dishCategoryId: dish.dishCategoryId,
        dishCategoryName: dish.dishCategoryName,
        options: [...dish.options],
      };
    }

    return acc;
  }, {});

  const conjoinedDishesArray = Object.values(conjoinedDishes);
  // console.log(orders);
  return (
    <Stack>
      <MoneyDivideInfo
        initTotal={orders.userCnt}
        initPortion={1}
        totalPrice={orders.price}
      />
      {conjoinedDishesArray.map((dish) => {
        const optionDescription = dish.options
          ?.map((option) => option.choiceName)
          .join(", ");
        return (
          <Box>
            <DishItemCard
              dish={{ ...dish, description: optionDescription || "" }}
              hasImage={false}
            >
              <Typography fontSize={12}>
                총 수량 <span style={{ fontSize: 16 }}>{dish.quantity}</span>개
              </Typography>
            </DishItemCard>
            <Divider variant="middle" />
          </Box>
        );
      })}
    </Stack>
  );
};

export default MoneyDivideList;
