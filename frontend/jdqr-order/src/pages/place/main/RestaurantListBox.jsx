import React from "react"
import { Box, Divider, Stack, Typography } from "@mui/material"
import RestaurantItemCard from "../../../components/card/RestaurantItemCard"
import PeopleFilter from "./PeopleFilter"
import { colors } from "../../../constants/colors"
import MapListContainer from "../../../components/container/MapListContainer"

const RestaurantListBox = ({ restaurants }) => {
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
          display: "flex",
          justifyContent: restaurants.length > 0 ? "flex-start" : "center",
          alignItems: restaurants.length > 0 ? "flex-start" : "center",
          "&::-webkit-scrollbar": {
            display: "none",
          },
          "-ms-overflow-style": "none",
          "scrollbar-width": "none",
        }}
      >
        {restaurants.length > 0 ? (
          restaurants.map((restaurant, index) => (
            <Box
              key={index}
              sx={{
                marginBottom: index === restaurants.length - 1 ? "24px" : 0,
              }}
            >
              <RestaurantItemCard restaurant={restaurant} />
            </Box>
          ))
        ) : (
          <Typography
            color={colors.text.sub1}
            fontSize={16}
            sx={{ marginBottom: "24px" }}
          >
            검색 결과가 없습니다
          </Typography>
        )}
      </Box>
    </MapListContainer>
  )
}

export default RestaurantListBox
