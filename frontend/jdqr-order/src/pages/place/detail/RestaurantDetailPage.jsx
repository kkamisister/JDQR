import React from "react"
import { Stack } from "@mui/material"
import MapListContainer from "../../../components/container/MapListContainer"
import RestaurantInfo from "./RestaurantInfo"

const RestaurantDetailPage = () => {
  return (
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
      <MapListContainer
        sx={{
          backgroundColor: "white",
          height: "50vh",
        }}
      >
        <RestaurantInfo />
      </MapListContainer>
    </div>
  )
}

export default RestaurantDetailPage
