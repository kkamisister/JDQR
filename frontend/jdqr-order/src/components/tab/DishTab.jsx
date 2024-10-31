import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import { useState } from "react";
import { colors } from "../../constants/colors";
export default function DishTab({ dishCategories }) {
  const [value, setValue] = useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box sx={{ bgcolor: colors.background.white }}>
      <Tabs
        value={value}
        onChange={handleChange}
        variant="scrollable"
        scrollButtons="auto"
        aria-label="scrollable auto tabs example"
        textColor={colors.text.main}
        TabIndicatorProps={{
          style: {
            backgroundColor: colors.text.sub3,
          },
        }}
      >
        {dishCategories.map((category, index) => (
          <Tab
            key={index}
            label={category}
            sx={{
              fontWeight: "bold",
              fontSize: "16px",
              width: "25%",
            }}
          />
        ))}
      </Tabs>
    </Box>
  );
}
