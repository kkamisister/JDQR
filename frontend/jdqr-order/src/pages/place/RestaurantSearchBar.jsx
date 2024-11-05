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
        width: "90%",
        maxWidth: "600px",
      }}
    >
      <TextField
        placeholder="매장 검색"
        fullWidth
        sx={{
          borderRadius: "10px",
          backgroundColor: colors.background.white,
          boxShadow: "0px 4px 12px rgba(0, 0, 0, 0.1)",
          "& .MuiOutlinedInput-root": {
            "& fieldset": {
              borderColor: "transparent",
            },
            "&:hover fieldset": {
              borderColor: "transparent",
            },
            "&.Mui-focused fieldset": {
              borderColor: "transparent",
            },
          },
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
