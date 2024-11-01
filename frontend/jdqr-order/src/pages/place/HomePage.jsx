import React from "react"
import KakaoMap from "../../components/map/KakaoMap"
import RestaurantList from "./RestaurantList"

const HomePage = () => {
  return (
    <div>
      <KakaoMap />
      <RestaurantList />
    </div>
  )
}

export default HomePage
