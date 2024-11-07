import React from "react"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantListBox from "./RestaurantListBox"
import MapDefaultHeader from "../../../components/header/MapDefaultHeader"

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
        <MapDefaultHeader />
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
        <RestaurantListBox />
      </div>
    </>
  )
}

export default HomePage
