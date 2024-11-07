import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import { useState } from "react";
import { colors } from "../../constants/colors";

export default function PaymentTab({ orderList, onTabClick }) {
  const tabs = ["함께 결제", "각자 결제"];
  const [value, setValue] = useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
    onTabClick(orderList[newValue]);
  };

  return (
    <Box sx={{ bgcolor: colors.background.white }}>
      <Tabs
        value={value}
        onChange={handleChange}
        sx={{
          color: colors.text.main,
        }}
        TabIndicatorProps={{
          style: {
            backgroundColor: colors.main.primary500,
          },
        }}
      >
        {tabs.map((tab, index) => (
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
