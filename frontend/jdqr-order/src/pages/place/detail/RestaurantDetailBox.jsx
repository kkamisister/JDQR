import React from "react"
import RestaurantInfo from "./RestaurantInfo"
import MapListContainer from "../../../components/container/MapListContainer"
import DishTab from "../../../components/tab/DishTab"
import RestaurantDetailDishItemCard from "../../../components/card/RestaurantDetailDishItemCard"

const RestaurantDetailBox = ({ categories, dishes, restaurant }) => {
  return (
    <MapListContainer>
      <RestaurantInfo restaurant={restaurant} />
      <DishTab dishCategories={categories} />

      {dishes.map((dishCategory) =>
        dishCategory.items.map((dish) => (
          <RestaurantDetailDishItemCard key={dish.dishId} dish={dish} />
        ))
      )}
    </MapListContainer>
  )
}

export default RestaurantDetailBox
