import Tabs from "@mui/material/Tabs"
import Tab from "@mui/material/Tab"
import Box from "@mui/material/Box"
import { useState } from "react"
import { colors } from "../../constants/colors"
export default function DishTab({ dishCategories, onTabClick }) {
  const [value, setValue] = useState(0)

  const handleChange = (event, newValue) => {
    setValue(newValue)
    onTabClick(dishCategories[newValue])
  }

  return (
    <Box sx={{ bgcolor: colors.background.white }}>
      <Tabs
        value={value}
        onChange={handleChange}
        variant="scrollable"
        scrollButtons="auto"
        aria-label="scrollable auto tabs example"
        textColor="inherit"
        TabIndicatorProps={{
          style: {
            backgroundColor: colors.main.primary500,
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
              minWidth: "25%",
            }}
          />
        ))}
      </Tabs>
    </Box>
  )
}
