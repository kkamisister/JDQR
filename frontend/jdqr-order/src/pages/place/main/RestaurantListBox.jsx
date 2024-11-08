import React from "react"
import { Box, Divider, Stack } from "@mui/material"
import RestaurantItemCard from "../../../components/card/RestaurantItemCard"
import PeopleFilter from "./PeopleFilter"
import { colors } from "../../../constants/colors"
import MapListContainer from "../../../components/container/MapListContainer"

const RestaurantListBox = () => {
  const restaurantItems = [1, 2, 3, 4, 5, 6, 7, 8, 9]

  return (
    <MapListContainer
      spacing={3}
      sx={{
        height: "100%",
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

      <Box
        sx={{
          overflowY: "auto",
          flexGrow: 1,
          "&::-webkit-scrollbar": {
            display: "none",
          },
          "-ms-overflow-style": "none",
          "scrollbar-width": "none",
        }}
      >
        {restaurantItems.map((item, index) => (
          <RestaurantItemCard key={index} />
        ))}
      </Box>
    </MapListContainer>
  )
}

export default RestaurantListBox
