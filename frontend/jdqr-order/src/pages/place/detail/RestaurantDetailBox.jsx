import React from "react"
import RestaurantInfo from "./RestaurantInfo"
import DishTab from "../../../components/tab/DishTab"
import RestaurantDetailDishItemCard from "../../../components/card/RestaurantDetailDishItemCard"
import { Stack } from "@mui/material"

const RestaurantDetailBox = ({ categories, dishes, restaurant }) => {
  return (
    <Stack padding="10px" height="100%">
      <RestaurantInfo restaurant={restaurant} />
      <DishTab dishCategories={categories} />

      {dishes.map((dishCategory) =>
        dishCategory.items.map((dish) => (
          <RestaurantDetailDishItemCard key={dish.dishId} dish={dish} />
        ))
      )}
    </Stack>
  )
}

export default RestaurantDetailBox
