import React from "react"
import { Stack, TextField, InputAdornment } from "@mui/material"
import SearchIcon from "@mui/icons-material/Search"
import { colors } from "../../constants/colors"

const RestaurantSearchBar = () => {
  return (
    <Stack
      direction="row"
      justifyContent="center"
      alignItems="center"
      sx={{
        position: "absolute",
        top: "20px",
        left: "50%",
        transform: "translateX(-50%)",
        zIndex: 1000,
        width: "90%",
        maxWidth: "600px",
      }}
    >
      <TextField
        placeholder="매장 검색"
        variant="outlined"
        fullWidth
        sx={{
          borderRadius: "20px",
          backgroundColor: colors.background.white, // 약간의 투명도 있는 흰색 배경
          boxShadow: "0px 4px 12px rgba(0, 0, 0, 0.1)", // 부드러운 그림자
        }}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon />
            </InputAdornment>
          ),
        }}
      />
    </Stack>
  )
}

export default RestaurantSearchBar
