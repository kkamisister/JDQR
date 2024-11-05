import React, { useState } from "react"
import { Box, Divider, Stack } from "@mui/material"
import RestaurantItemCard from "../../components/card/RestaurantItemCard"
import PeopleFilter from "./PeopleFilter"
import { colors } from "../../constants/colors"

const RestaurantList = () => {
  return (
    <Stack
      direction="column"
      spacing={2}
      sx={{
        position: "sticky",
        bottom: 0,
        width: "100%",
        backgroundColor: colors.background.white,
        borderTopLeftRadius: "35px",
        borderTopRightRadius: "35px",
        padding: "10px",
        overflowY: "auto",
      }}
    >
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
    </Stack>
  )
}

export default RestaurantList
