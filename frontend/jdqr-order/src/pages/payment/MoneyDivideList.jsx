import { Stack } from "@mui/material";
import MoneyDivideInfo from "./MoneyDivideInfo";

const MoneyDivideList = ({ orders }) => {
  console.log(orders);
  const dishes = orders.orders.flatMap((order) => order.dishes);

  const conjoinedDishes = dishes.reduce((acc, dish) => {
    // 모든 옵션 정보를 key에 반영
    const key = `${dish.dishId}-${dish.options
      ?.map((option) => `${option.optionId}-${option.choiceId}`) // optionId-choiceId 형태로 변환
      .sort() // 옵션을 정렬
      .join("_")}`; // 정렬된 옵션을 병합
    console.log(`${dish.dishName}의 key값은 ${key}`);

    if (acc[key]) {
      // 동일한 key가 존재하면 totalPrice와 quantity를 합침
      acc[key].totalPrice += dish.price * dish.quantity;
      acc[key].quantity += dish.quantity;
    } else {
      // 새로운 key 생성
      acc[key] = {
        dishId: dish.dishId,
        dishName: dish.dishName,
        price: dish.price,
        totalPrice: dish.price * dish.quantity,
        quantity: dish.quantity,
        dishCategoryId: dish.dishCategoryId,
        dishCategoryName: dish.dishCategoryName,
        options: [...dish.options], // 모든 옵션 정보 유지
      };
    }

    return acc;
  }, {});

  const conjoinedDishesArray = Object.values(conjoinedDishes);

  console.log(conjoinedDishesArray);
  return (
    <Stack>
      <MoneyDivideInfo initTotal={orders.userCnt} initPortion={1} />
    </Stack>
  );
};

export default MoneyDivideList;
