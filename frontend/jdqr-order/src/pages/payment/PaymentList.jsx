import { Stack, Box, Typography, Divider } from "@mui/material";
import PaymentTab from "./PaymentTab";
import { useState } from "react";
import { colors } from "../../constants/colors";
import MoneyDivideList from "./MoneyDivideList";
import MenuDivideList from "./MenuDivideList";
import useWebSocketStore from "../../stores/SocketStore";

export default function PaymentList({ orders }) {
  // console.log(orders);
  const [activeTab, setActiveTab] = useState(0);
  const handleTabChange = (index) => {
    setActiveTab(index);
  };

  return (
    <Stack>
      <PaymentTab
        orderList={["금액 분할 결제", "메뉴별 결제"]}
        onTabClick={handleTabChange}
        activeTab={activeTab}
      />
      {activeTab === 0 && <MoneyDivideList orders={orders} />}
      {activeTab === 1 && <MenuDivideList orders={orders} />}
    </Stack>
  );
}
