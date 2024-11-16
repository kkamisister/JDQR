import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import { useState } from "react";
import { colors } from "../../constants/colors";

export default function PaymentTab({ orderList, onTabClick, activeTab }) {
  return (
    <Box>
      <Tabs
        value={activeTab}
        onChange={(e, newVal) => onTabClick(newVal)}
        textColor="inherit"
        TabIndicatorProps={{
          style: {
            backgroundColor: colors.main.primary500,
          },
        }}
      >
        {orderList.map((tab, index) => (
          <Tab
            key={index}
            label={tab}
            sx={{
              fontWeight: "bold",
              fontSize: "16px",
              minWidth: "50%",
            }}
          />
        ))}
      </Tabs>
    </Box>
  );
}
