import React from "react"
import RestaurantInfo from "./RestaurantInfo"
import MapListContainer from "../../../components/container/MapListContainer"

const RestaurantDetailBox = () => {
  return (
    <MapListContainer
      sx={{
        backgroundColor: "black",
      }}
    >
      <RestaurantInfo />
    </MapListContainer>
  )
}

export default RestaurantDetailBox
