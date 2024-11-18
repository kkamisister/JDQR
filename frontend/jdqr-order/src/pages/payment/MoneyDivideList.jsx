import { Stack } from "@mui/material";
import MoneyDivideInfo from "./MoneyDivideInfo";
import MoneyDivideListItem from "./MoneyDivideListItem";
import BaseButton from "../../components/button/BaseButton";
import { moneyDivide } from "../../utils/apis/order";
import { useState, useEffect } from "react";

const MoneyDivideList = ({ orders }) => {
  const dishes = orders.orders.flatMap((order) => order.dishes);
  const [total, setTotal] = useState(orders.userCnt);
  const [portion, setPortion] = useState(1);
  const [money, setMoney] = useState(0);

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
  const moneyPay = async () => {
    try {
      const response = await moneyDivide({
        peopleNum: total,
        serveNum: portion,
      });
      console.log("결제 성공:", response);
    } catch (error) {
      console.error("결제 요청 중 에러 발생:", error);
      alert("결제 요청 중 문제가 발생했습니다. 다시 시도해 주세요.");
    }
  };

  const handleVaulesChange = ({ total, portion, money }) => {
    setTotal(total);
    setPortion(portion);
    setMoney(money);
  };

  return (
    <Stack>
      <MoneyDivideInfo
        initTotal={orders.userCnt}
        totalPrice={orders.price}
        onValuesChange={handleVaulesChange}
      />
      {conjoinedDishesArray.map((dish) => (
        <MoneyDivideListItem key={dish.key} dish={dish} />
      ))}
      <BaseButton onClick={moneyPay}>
        {money.toLocaleString()}원 결제하기
      </BaseButton>
    </Stack>
  );
};

export default MoneyDivideList;
