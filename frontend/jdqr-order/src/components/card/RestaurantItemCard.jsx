import { Avatar, Box, Chip, Stack, Typography } from "@mui/material"
import React from "react"
import { colors } from "../../constants/colors"

const RestaurantItemCard = ({
  restaurantName,
  restaurantCategories,
  restaurant,
  leftSeats,
  leftTables,
  open,
  dishCategories,
  dishes,
  children,
  sx,
}) => {
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
        height: "80px",
        alignItems: "center",
        "&:hover": {
          backgroundColor: colors.background.box,
        },
        transition: "all 0.3s ease", // transition 적용
        cursor: "pointer",
        ...sx,
      }}
    >
      <Avatar // 1. 식당 이미지
        sx={{
          borderRadius: "10px",
          width: 60,
          height: 60,
        }}
      />
      <Stack // 2. 텍스트영역
        direction="column"
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
            fontSize={17}
            fontWeight={700}
            sx={{
              whiteSpace: "nowrap",
            }}
          >
            츄라우미
            {/* {restaurantName} */}
          </Typography>
          <Typography
            fontSize={14}
            color={colors.text.sub1}
            sx={{
              whiteSpace: "nowrap",
            }}
          >
            이자카야
            {/* {restaurantCategories} */}
          </Typography>
        </Stack>
        <Stack
          direction="row"
          spacing={1}
          sx={{ flex: 1, alignItems: "center" }}
        >
          <Chip
            label={open ? "영업중" : "영업종료"}
            sx={{
              padding: "0px",
              fontSize: "12px",
              fontWeight: "bold",
              height: "22px",
              backgroundColor: colors.background.box,
              color: open ? colors.point.red : colors.text.sub1,
            }}
          ></Chip>
          <Typography
            fontSize={12}
            fontWeight={600}
            color={colors.text.sub1}
            sx={{
              whiteSpace: "nowrap",
            }}
          >
            최대 6인 테이블
          </Typography>
        </Stack>
      </Stack>
      <Stack // 3. 좌석, 테이블 현황
        direction="row"
        spacing={1.5}
        sx={{
          justifyContent: "space-evenly",
          flex: 1,
        }}
      >
        <Stack
          direction="column"
          spacing={0.5}
          sx={{
            backgroundColor: "#ffffff",
            padding: "4px, 8px",
            borderRadius: "8px",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            width: "65px",
            height: "65px",
            border: `1px solid ${colors.main.primary300}`,
          }}
        >
          <Typography
            fontSize={17}
            fontWeight={600}
            color={!open ? colors.main.primary300 : colors.text.main}
          >
            {open ? { leftSeats } : "16석"}
          </Typography>
          <Typography fontSize={11} color={colors.text.sub1}>
            잔여좌석
          </Typography>
        </Stack>
        <Stack
          direction="column"
          spacing={0.5}
          sx={{
            backgroundColor: "#ffffff",
            padding: "4px, 8px",
            borderRadius: "8px",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            width: "65px",
            height: "65px",
            border: `1px solid ${colors.main.primary300}`,
          }}
        >
          <Typography
            fontSize={17}
            fontWeight={600}
            color={!open ? colors.main.primary300 : colors.text.main}
          >
            {open ? { leftTables } : "5T"}
          </Typography>
          <Typography fontSize={11} color={colors.text.sub1}>
            잔여테이블
          </Typography>
        </Stack>
      </Stack>
    </Stack>
  )
}

export default RestaurantItemCard
