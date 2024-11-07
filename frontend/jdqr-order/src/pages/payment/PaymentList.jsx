import { Stack, Box, Typography, Divider } from "@mui/material";
import PaymentTab from "./PaymentTab";
import BaseButton from "../../components/button/BaseButton";
import DishItemCard from "../../components/card/DishItemCard";
import MoneyDiviard from "../../components/card/DishItemCard";
import MoneyDivideInfo from "./MoneyDivideInfo";
import { useState } from "react";
import { colors } from "../../constants/colors";

const mockData = {
  orderId: 1,
  tableName: "영표의 식탁",
  dishCnt: 27, // 담은 음식의 총 개수
  price: 202600, // 담은 음식의 총 가격
  orders: [
    {
      dishId: 4,
      userId: "d8ba9920-08f6-4f65-b7df-811ae20e70d1",
      dishName: "더블쿼터파운드치즈버거",
      dishCategoryId: 1,
      dishCategoryName: "햄버거",
      price: 12800, // 옵션 가격이 포함되지 않은 메뉴 가격
      options: [],
      quantity: 14,
    },
    {
      dishId: 4,
      userId: "57c7fd62-8c66-4a62-9f12-e6b07e1c40d2",
      dishName: "더블쿼터파운드치즈버거",
      dishCategoryId: 1,
      dishCategoryName: "햄버거",
      price: 12800, // 옵션 가격이 포함되지 않은 메뉴 가격
      options: [
        {
          optionId: 1,
          optionName: "사이드 메뉴",
          choiceId: 1,
          choiceName: "치즈스틱",
          price: 500, // 해당 옵션의 가격
        },
      ],
      quantity: 6,
    },
  ],
};
export default function PaymentList() {
  const aggregateDishes = (data) => {
    const aggregatedDishes = {};

    data.orders.forEach((dish) => {
      // options 배열을 통해 각 optionId와 choiceId를 문자열로 결합하여 고유 키 생성
      const optionsKey = dish.options
        .map((option) => `${option.optionId}-${option.choiceId}`)
        .join("_");
      const uniqueKey = `${dish.dishId}-${optionsKey}`;

      // 동일한 uniqueKey가 이미 존재하면 수량을 합산, 없으면 새로 추가
      if (aggregatedDishes[uniqueKey]) {
        aggregatedDishes[uniqueKey].quantity += dish.quantity;
      } else {
        aggregatedDishes[uniqueKey] = { ...dish };
      }
    });

    return Object.values(aggregatedDishes);
  };

  const aggregatedDishes = aggregateDishes(mockData);

  const onClick = () => {};
  const count = mockData.dishCnt;
  const totalPrice = mockData.price;

  const [selectedTab, setSelectedTab] = useState("함께 결제");

  const handleTabClick = (selectedOrder) => {
    setSelectedTab(selectedOrder);
  };

  return (
    <Stack>
      <PaymentTab
        orderList={["함께 결제", "각자 결제"]}
        onTabClick={handleTabClick}
      />
      {selectedTab === "함께 결제" && (
        <MoneyDivideInfo initTotal={1} initPortion={1} />
      )}
      {aggregatedDishes.map((dish) => (
        <>
          <DishItemCard
            key={`${dish.dishId}-${dish.options
              .map((option) => `${option.optionId}-${option.choiceId}`)
              .join("_")}`}
            dish={dish}
            onClick={onClick}
            hasImage={false}
            hasOption={true}
            sx={{
              mx: 2,
            }}
          >
            <Typography sx={{ fontSize: 12, color: colors.text.main }}>
              총수량: <span style={{ fontSize: "14px" }}>{dish.quantity}</span>
              개
            </Typography>
          </DishItemCard>
          <Divider variant="middle" />
        </>
      ))}
      <BaseButton count={count} onClick={onClick}>
        {`${totalPrice.toLocaleString()}원 결제하기`}
      </BaseButton>
    </Stack>
  );
}
