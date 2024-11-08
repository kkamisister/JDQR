import React from "react"
import { Stack } from "@mui/material"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantListBox from "./RestaurantListBox"
import MapDefaultHeader from "../../../components/header/MapDefaultHeader"

const HomePage = () => {
  return (
    <Stack>
      <Stack
        sx={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          zIndex: 1,
        }}
      >
        <MapDefaultHeader />
      </Stack>

      <Stack
        sx={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          width: "100%",
          height: "100%",
        }}
      >
        <KakaoMap />
      </Stack>

      <Stack
        sx={{
          position: "relative",
          bottom: -700,
          left: 0,
          right: 0,
          maxHeight: "80VH",
          zIndex: 1,
          overflowY: "auto",
        }}
      >
        <RestaurantListBox />
      </Stack>
    </Stack>
  )
}

export default HomePage
