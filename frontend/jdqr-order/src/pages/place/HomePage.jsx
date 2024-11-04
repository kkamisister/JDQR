import React from "react"
import KakaoMap from "../../components/map/KakaoMap"
import RestaurantList from "./RestaurantList"
import RestaurantSearchBar from "./RestaurantSearchBar"

const HomePage = () => {
  return (
    <div>
      <RestaurantSearchBar />
      <KakaoMap />
      <RestaurantList />
    </div>
  )
}

export default HomePage
