import React from "react"
import { Stack, Avatar, Typography, Chip } from "@mui/material"
import { colors } from "../../../constants/colors"

const RestaurantInfo = ({ restaurant, open }) => {
  return (
    <Stack
      direction="row"
      spacing={2}
      sx={{
        backgroundColor: colors.background.white,
        padding: "10px",
        borderRadius: "10px",
        alignItems: "center",
        width: "100%",
      }}
    >
      <Avatar // 1. 식당 이미지
        src={restaurant.image}
        alt={restaurant.restaurantName || "Restaurant Image"}
        sx={{
          borderRadius: "10px",
          width: 78,
          height: 78,
        }}
      />
      <Stack // 2. 텍스트 영역
        spacing={1}
        sx={{
          justifyContent: "flex-start",
        }}
      >
        <Stack
          direction="row"
          spacing={1}
          sx={{ flex: 1, alignItems: "center" }}
        >
          <Typography
            fontSize={20}
            fontWeight={700}
            sx={{
              whiteSpace: "nowrap",
            }}
          >
            {restaurant.restaurantName}
          </Typography>
          <Typography
            fontSize={17}
            color={colors.text.sub1}
            sx={{
              whiteSpace: "nowrap",
            }}
          >
            {restaurant.restaurantCategories.major[0].restaurantCategoryName}
          </Typography>
          <Typography
            fontSize={13}
            color={colors.text.sub2}
            sx={{
              whiteSpace: "nowrap",
              textDecoration: "underline",
            }}
          >
            {restaurant.phoneNumber}
          </Typography>
        </Stack>
        <Stack
          direction="row"
          spacing={2}
          sx={{ flex: 1, alignItems: "center" }}
        >
          <Stack direction="row" spacing={1}>
            <Chip
              label={restaurant.open ? `${restaurant.restSeatNum}석` : "-"}
              sx={{
                width: "55px",
                height: "30px",
                fontSize: "15px",
                borderRadius: "10px",
                backgroundColor: colors.background.box,
                color:
                  restaurant.open && restaurant.restSeatNum > 0
                    ? colors.main.primary500
                    : colors.text.sub1,
                ".MuiChip-label": {
                  padding: 0,
                },
              }}
            />
            <Chip
              label={restaurant.open ? `${restaurant.restTableNum}T` : "-"}
              sx={{
                width: "55px",
                height: "30px",
                fontSize: "15px",
                borderRadius: "10px",
                backgroundColor: colors.background.box,
                color:
                  restaurant.open && restaurant.restSeatNum > 0
                    ? colors.main.primary500
                    : colors.text.sub1,
                ".MuiChip-label": {
                  padding: 0,
                },
              }}
            />
          </Stack>
          <Typography
            fontSize={13}
            fontWeight={600}
            color={colors.text.sub1}
            sx={{
              whiteSpace: "nowrap",
            }}
          >
            {restaurant.maxPeopleNum > 0
              ? `최대 ${restaurant.maxPeopleNum}인 테이블`
              : "잔여 테이블 없음"}
          </Typography>
        </Stack>
      </Stack>
    </Stack>
  )
}

export default RestaurantInfo
