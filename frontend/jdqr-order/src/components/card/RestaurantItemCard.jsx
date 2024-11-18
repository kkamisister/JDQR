import { Avatar, Box, Chip, Stack, Typography } from "@mui/material"
import React from "react"
import { colors } from "../../constants/colors"

const RestaurantItemCard = ({ restaurant, sx, onClick }) => {
  return (
    <Stack
      direction="row"
      spacing={2}
      onClick={onClick}
      sx={{
        backgroundColor: colors.background.white,
        padding: "10px",
        borderRadius: "10px",
        alignItems: "center",
        width: "100%",
        height: "80px",
        cursor: "pointer",
        "&:hover": {
          backgroundColor: colors.background.primary,
        },
        transition: "all 0.3s ease",
        ...sx,
      }}
    >
      <Avatar
        src={restaurant.image}
        alt={restaurant.restaurantName || "Restaurant Image"}
        sx={{
          borderRadius: "10px",
          width: 55,
          height: 55,
        }}
      />
      <Stack
        direction="column"
        spacing={1}
        sx={{ justifyContent: "flex-start" }}
      >
        <Stack
          sx={{
            flex: 1,
            alignItems: "flex-start",
            maxWidth: "130px",
            flexDirection: "row",
            "@media (max-width: 350px)": {
              flexDirection: "column",
            },
          }}
        >
          <Typography
            fontSize={17}
            fontWeight={700}
            sx={{ whiteSpace: "nowrap" }}
          >
            {restaurant.restaurantName}
          </Typography>

          <Stack
            direction="row"
            spacing={0.5}
            sx={{
              "@media (max-width: 350px)": {
                flexWrap: "wrap",
              },
              "@media (min-width: 350px)": {
                paddingLeft: "10px",
              },
            }}
          >
            {restaurant.restaurantCategories.minor.map((category) => (
              <Typography
                key={category.restaurantCategoryId}
                fontSize={14}
                color={colors.text.sub1}
                sx={{ whiteSpace: "nowrap" }}
              >
                {category.restaurantCategoryName}
              </Typography>
            ))}
          </Stack>
        </Stack>

        <Stack
          direction="row"
          spacing={1}
          sx={{ flex: 1, alignItems: "center" }}
        >
          <Chip
            label={restaurant.open ? "영업중" : "영업 종료"}
            sx={{
              width: "45px",
              fontSize: "9px",
              height: "22px",
              backgroundColor: colors.background.box,
              color: restaurant.open ? colors.point.red : colors.text.sub1,
              ".MuiChip-label": {
                padding: 0,
              },
            }}
          />
          <Typography
            fontSize={12}
            fontWeight={600}
            color={colors.text.sub1}
            sx={{ whiteSpace: "nowrap" }}
          >
            {restaurant.maxPeopleNum > 0
              ? `최대 ${restaurant.maxPeopleNum}인 테이블`
              : "잔여 테이블 없음"}
          </Typography>
        </Stack>
      </Stack>
      <Stack
        direction="row"
        spacing={1}
        sx={{ justifyContent: "space-evenly", flex: 1 }}
      >
        <Stack
          direction="column"
          spacing={0.5}
          sx={{
            backgroundColor: "#ffffff",
            padding: "4px 0",
            borderRadius: "8px",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            width: "55px",
            height: "55px",
            border: `1px solid ${colors.text.sub3}`,
          }}
        >
          <Typography
            fontSize={15}
            fontWeight={600}
            color={
              restaurant.open && restaurant.restSeatNum > 0
                ? colors.main.primary500
                : colors.text.main
            }
          >
            {restaurant.open ? `${restaurant.restSeatNum}석` : "-"}
          </Typography>
          <Typography fontSize={9} color={colors.text.sub1}>
            잔여좌석
          </Typography>
        </Stack>
        <Stack
          direction="column"
          spacing={0.5}
          sx={{
            backgroundColor: "#ffffff",
            padding: "4px 0",
            borderRadius: "8px",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            width: "55px",
            height: "55px",
            border: `1px solid ${colors.text.sub3}`,
          }}
        >
          <Typography
            fontSize={15}
            fontWeight={600}
            color={
              restaurant.open && restaurant.restTableNum > 0
                ? colors.main.primary500
                : colors.text.main
            }
          >
            {restaurant.open ? `${restaurant.restTableNum}T` : "-"}
          </Typography>
          <Typography fontSize={9} color={colors.text.sub1}>
            잔여테이블
          </Typography>
        </Stack>
      </Stack>
    </Stack>
  )
}

export default RestaurantItemCard
