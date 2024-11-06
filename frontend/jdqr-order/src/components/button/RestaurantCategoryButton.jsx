import React from "react"
import { Button } from "@mui/material"
import { colors } from "../../constants/colors"

const RestaurantCategoryButton = ({ name, isActive, onClick }) => {
  return (
    <Button
      onClick={onClick}
      sx={{
        minWidth: 0,
        width: "50px",
        height: "30px",
        padding: 0,
        backgroundColor: colors.background.white,
        border: isActive
          ? `1px solid ${colors.main.primary200}`
          : `1px solid ${colors.background.box}`,
        borderRadius: "10px",
        color: isActive ? colors.main.primary400 : colors.text.sub2,
      }}
    >
      {name}
    </Button>
  )
}

export default RestaurantCategoryButton
