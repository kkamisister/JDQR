import React from "react"
import { Button } from "@mui/material"
import { colors } from "../../constants/colors"

const RestaurantCategoryButton = ({ name, isActive, onClick }) => {
  const activeStyles = {
    backgroundColor: colors.main.primary100,
    color: colors.main.primary400,
  }

  const defaultStyles = {
    backgroundColor: colors.background.white,
    color: colors.text.sub2,
  }

  return (
    <Button
      onClick={onClick}
      sx={{
        minWidth: 0,
        width: "55px",
        height: "32px",
        padding: 0,
        borderRadius: "15px",
        boxShadow: "0px 4px 12px rgba(0, 0, 0, 0.1)",
        fontSize: 12,
        ...(isActive ? activeStyles : defaultStyles),
      }}
    >
      {name}
    </Button>
  )
}

export default RestaurantCategoryButton
