import React from "react"
import KakaoMap from "../../components/map/KakaoMap"
import RestaurantList from "./RestaurantList"
import RestaurantSearchBar from "./RestaurantSearchBar"

const HomePage = () => {
  return (
    <>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
        }}
      >
        <RestaurantSearchBar />
      </div>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          zIndex: -1,
        }}
      >
        <KakaoMap />
      </div>
      <div
        style={{
          position: "fixed",
          bottom: 0,
          left: 0,
          right: 0,
          marginTop: "105px",
          overflowY: "auto",
        }}
      >
        <RestaurantList />
      </div>
    </>
  )
}

export default HomePage
