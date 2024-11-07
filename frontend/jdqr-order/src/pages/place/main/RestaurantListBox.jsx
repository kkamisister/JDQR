import React from "react"
import { Box, Divider, Stack } from "@mui/material"
import RestaurantItemCard from "../../../components/card/RestaurantItemCard"
import PeopleFilter from "./PeopleFilter"
import { colors } from "../../../constants/colors"
import MapListContainer from "../../../components/container/MapListContainer"

const RestaurantListBox = () => {
  // RestaurantItemCard를 동적으로 렌더링할 배열
  const restaurantItems = [1, 2, 3, 4]

  return (
    <MapListContainer spacing={2}>
      <Stack
        sx={{
          height: "20px",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Box
          sx={{
            height: "5px",
            width: "50px",
            backgroundColor: colors.background.box,
            margin: "0 auto",
          }}
        />
      </Stack>

      <PeopleFilter />

      <Divider
        sx={{
          borderColor: colors.background.box,
          height: "1px",
        }}
      />

      {restaurantItems.map((item, index) => (
        <RestaurantItemCard key={index} />
      ))}
    </MapListContainer>
  )
}

export default RestaurantListBox
