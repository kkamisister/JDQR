import React from "react"
import { Box, Divider, Stack } from "@mui/material"
import RestaurantItemCard from "../../../components/card/RestaurantItemCard"
import PeopleFilter from "./PeopleFilter"
import { colors } from "../../../constants/colors"
import MapListBox from "../../../components/box/MapListBox"

const RestaurantList = () => {
  return (
    <MapListBox>
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
      <RestaurantItemCard />
      <RestaurantItemCard />
      <RestaurantItemCard />
      <RestaurantItemCard />
    </MapListBox>
  )
}

export default RestaurantList
