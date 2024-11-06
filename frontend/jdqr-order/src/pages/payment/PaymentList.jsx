import { Stack, Box } from "@mui/material";
import PaymentTab from "./PaymentTab";
import BaseButton from "../../components/button/BaseButton";
import DishItemCard from "../../components/card/DishItemCard";
import MoneyDivideInfo from "./MoneyDivideInfo";
import { useState } from "react";
export default function PaymentList() {
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
        dishPrice: 12800, // 옵션 가격이 포함되지 않은 메뉴 가격
        options: [],
        quantity: 14,
      },
      {
        dishId: 4,
        userId: "57c7fd62-8c66-4a62-9f12-e6b07e1c40d2",
        dishName: "더블쿼터파운드치즈버거",
        dishCategoryId: 1,
        dishCategoryName: "햄버거",
        dishPrice: 12800, // 옵션 가격이 포함되지 않은 메뉴 가격
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
      {selectedTab === "함께 결제" && <MoneyDivideInfo />}

      <BaseButton count={count} onClick={onClick}>
        {`${totalPrice.toLocaleString()}원 결제하기`}
      </BaseButton>
    </Stack>
  );
}
