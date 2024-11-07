import React from "react"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantDetailBox from "./RestaurantDetailBox"
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader"

const RestaurantDetailPage = () => {
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
        <MapBackButtonHeader />
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
        }}
      >
        <RestaurantDetailBox />
      </div>
    </>
  )
}

export default RestaurantDetailPage
