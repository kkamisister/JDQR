import React, { useState } from "react"
import { Stack, TextField, InputAdornment } from "@mui/material"
import SearchIcon from "@mui/icons-material/Search"
import RestaurantCategoryButton from "../button/RestaurantCategoryButton"
import { colors } from "../../constants/colors"

const MapDefaultHeader = ({ majorCategories, setKeyword }) => {
  const [activeIndex, setActiveIndex] = useState(null)
  const [isChecked, setIsChecked] = useState(false)

  const handleButtonClick = (index) => {
    setActiveIndex((prevIndex) => {
      const newIndex = prevIndex === index ? null : index
      setIsChecked(newIndex !== null)
      return newIndex
    })
  }

  const handleKeywordChange = (event) => {
    setKeyword(event.target.value)
  }

  return (
    <Stack
      spacing={1}
      alignItems="center"
      sx={{ width: "100%", height: "auto" }}
    >
      <Stack
        direction="row"
        justifyContent="center"
        alignItems="center"
        sx={{
          position: "fixed",
          top: "20px",
          left: "50%",
          transform: "translateX(-50%)",
          width: "90%",
          maxWidth: "600px",
          zIndex: 10,
        }}
      >
        <TextField
          placeholder="매장 검색"
          fullWidth
          onChange={handleKeywordChange}
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
      <Stack
        direction="row"
        justifyContent="space-between"
        alignItems="center"
        sx={{
          position: "fixed",
          top: "80px",
          left: "50%",
          transform: "translateX(-50%)",
          width: "90%",
          maxWidth: "600px",
          zIndex: 9,
        }}
      >
        {majorCategories.map((name, index) => (
          <RestaurantCategoryButton
            key={index}
            name={name}
            isActive={activeIndex === index}
            onClick={() => handleButtonClick(index)}
          />
        ))}
      </Stack>
    </Stack>
  )
}

export default MapDefaultHeader
