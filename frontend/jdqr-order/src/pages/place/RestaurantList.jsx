import React from "react"
import { Stack } from "@mui/material"
import RestaurantItemCard from "../../components/card/RestaurantItemCard"
import PeopleFilter from "./PeopleFilter"

const RestaurantList = () => {
  return (
    <Stack
      spacing={2}
      sx={{
        zIndex: 1,
        position: "absolute",
        bottom: 0,
        width: "100vw",
        height: "60%",
        backgroundColor: "white",
        borderTopLeftRadius: "35px",
        borderTopRightRadius: "35px",
      }}
    >
      <PeopleFilter />
      <RestaurantItemCard />
      <RestaurantItemCard />
      <RestaurantItemCard />
      <RestaurantItemCard />
    </Stack>
  )
}

export default RestaurantList
