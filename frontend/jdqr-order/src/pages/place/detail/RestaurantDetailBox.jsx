import React from "react"
import { scroller } from "react-scroll"
import RestaurantInfo from "./RestaurantInfo"
import DishTab from "../../../components/tab/DishTab"
import RestaurantDetailDishItemCard from "../../../components/card/RestaurantDetailDishItemCard"
import { Box, Stack } from "@mui/material"
import { colors } from "../../../constants/colors"

const RestaurantDetailBox = ({ categories, dishes, restaurant }) => {
  const handleCategoryClick = (category) => {
    scroller.scrollTo(category, {
      duration: 800,
      delay: 0,
      smooth: "easeInOutQuart",
      containerId: "scrollable-dish-list",
    })
  }
  return (
    <Stack padding="10px" height="100%">
      <RestaurantInfo restaurant={restaurant} />
      <DishTab dishCategories={categories} onTabClick={handleCategoryClick} />
      <Box
        id="scrollable-dish-list"
        sx={{
          flex: 1,
          py: 1,
          bgcolor: colors.background.box,
          color: colors.text.main,
          overflowY: "auto",
          "&::-webkit-scrollbar": {
            display: "none",
          },
          msOverflowStyle: "none",
          scrollbarWidth: "none",
        }}
      >
        {dishes.map((dishCategory) =>
          dishCategory.items.map((dish) => (
            <RestaurantDetailDishItemCard key={dish.dishId} dish={dish} />
          ))
        )}
      </Box>
    </Stack>
  )
}

export default RestaurantDetailBox
