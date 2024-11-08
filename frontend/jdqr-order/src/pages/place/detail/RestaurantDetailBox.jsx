import React from "react"
import RestaurantInfo from "./RestaurantInfo"
import MapListContainer from "../../../components/container/MapListContainer"
import DishTab from "../../../components/tab/DishTab"

const RestaurantDetailBox = ({ categories, dishes }) => {
  return (
    <MapListContainer>
      <RestaurantInfo />
      <DishTab dishCategories={categories} />
    </MapListContainer>
  )
}

export default RestaurantDetailBox
