import React from "react"
import { Box, Divider, Stack, Typography } from "@mui/material"
import RestaurantItemCard from "../../../components/card/RestaurantItemCard"
import PeopleFilter from "./PeopleFilter"
import { colors } from "../../../constants/colors"
import MapListContainer from "../../../components/container/MapListContainer"

const RestaurantListBox = ({
  restaurants,
  people,
  setPeople,
  together,
  setTogether,
}) => {
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

      <PeopleFilter
        people={people}
        setPeople={setPeople}
        together={together}
        setTogether={setTogether}
      />

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
        {restaurants.length > 0 ? (
          restaurants.map((restaurant, index) => (
            <RestaurantItemCard key={index} restaurant={restaurant} />
          ))
        ) : (
          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              height: "100%",
            }}
          >
            <Typography color={colors.text.sub1} fontSize={16}>
              검색 결과가 없습니다
            </Typography>
          </Box>
        )}
      </Box>
    </MapListContainer>
  )
}

export default RestaurantListBox
