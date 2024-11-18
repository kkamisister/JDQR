import React from "react"
import { Element, scroller } from "react-scroll"
import DishTab from "../../../components/tab/DishTab"
import { Box, Stack, Typography, Divider } from "@mui/material"
import { colors } from "../../../constants/colors"
import RestaurantDetailDishItemCard from "../../../components/card/RestaurantDetailDishItemCard"

const RestaurantDetailBox = ({ categories, dishes }) => {
  const handleCategoryClick = (category) => {
    // console.log("category:", category) // 클릭된 카테고리 이름
    scroller.scrollTo(category, {
      duration: 800,
      delay: 0,
      smooth: "easeInOutQuart",
      containerId: "scrollable-dish-list",
    })
  }

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        height: "100vh",
      }}
    >
      <DishTab dishCategories={categories} onTabClick={handleCategoryClick} />
      <Box
        id="scrollable-dish-list"
        sx={{
          flex: 1,
          pb: 1,
          bgcolor: colors.background.box,
          color: colors.text.main,
          overflowY: "auto",
          "&::-webkit-scrollbar": {
            display: "none",
          },
          msOverflowStyle: "none",
          scrollbarWidth: "none",
        }}
      >
        {dishes.map((category, index) => (
          <Element
            name={category.dishCategoryName}
            key={category.dishCategoryId}
          >
            <Stack
              sx={{
                bgcolor: colors.background.white,
                mb: 2,
                p: 1,
              }}
            >
              <Typography
                sx={{
                  fontWeight: 600,
                  fontSize: "16px",
                  p: "10px",
                }}
              >
                {category.dishCategoryName} {/* 카테고리명 */}
              </Typography>
              {category.items.map((dish, dishIndex) => (
                <Box key={dish.dishId}>
                  <RestaurantDetailDishItemCard dish={dish} />

                  {dishIndex < category.items.length - 1 && (
                    <Divider variant="middle" />
                  )}
                </Box>
              ))}
            </Stack>
          </Element>
        ))}
      </Box>
    </Box>
  )
}

export default RestaurantDetailBox
