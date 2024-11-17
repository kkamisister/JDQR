import { Stack } from "@mui/material";
import MoneyDivideInfo from "./MoneyDivideInfo";
import MoneyDivideListItem from "./MoneyDivideListItem";
import BaseButton from "../../components/button/BaseButton";
import { useState } from "react";
import { usePaymentStore } from "../../stores/paymentStore";

const MoneyDivideList = ({ orders }) => {
  const money = usePaymentStore((state) => state.money);
  const bill = Math.ceil(money).toLocaleString();
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
        key,
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

  return (
    <Stack>
      <MoneyDivideInfo
        initTotal={orders.userCnt}
        initPortion={1}
        totalPrice={orders.price}
      />
      {conjoinedDishesArray.map((dish) => (
        <MoneyDivideListItem key={dish.key} dish={dish} />
      ))}
      <BaseButton>{bill}원 결제하기</BaseButton>
    </Stack>
  );
};

export default MoneyDivideList;
