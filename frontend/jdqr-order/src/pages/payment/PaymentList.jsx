import { Stack } from "@mui/material";
import PaymentTab from "./PaymentTab";
import { useState } from "react";
import MoneyDivideList from "./MoneyDivideList";
import MenuDivideList from "./MenuDivideList";
import { useLocation } from "react-router-dom";

export default function PaymentList({ orders }) {
  const paymentType = orders.paymentType;
  const [activeTab, setActiveTab] = useState(
    paymentType === "MONEY_DIVIDE" ? 0 : paymentType === "MENU_DIVIDE" ? 1 : 0
  );

  const handleTabChange = (index) => {
    setActiveTab(index);
  };

  return (
    <Stack>
      <PaymentTab
        orderList={["금액 분할 결제", "메뉴별 결제"]}
        onTabClick={handleTabChange}
        activeTab={activeTab}
        paymentType={paymentType}
      />
      {activeTab === 0 && <MoneyDivideList orders={orders} />}
      {activeTab === 1 && <MenuDivideList orders={orders} />}
    </Stack>
  );
}
