import { useEffect, useState } from "react";
import { Element, scroller } from "react-scroll";
import DishSearchBar from "./DishSearchBar";
import { Box, Divider, Stack, Typography } from "@mui/material";
import DishTab from "../../components/tab/DishTab";
import DishItemCard from "../../components/card/DishItemCard";
import { colors } from "../../constants/colors";

export default function DishList({ dishes }) {
  const categories = [
    "인기 메뉴",
    "아이거진짜좋은메뉴인데얼마나좋냐면",
    "피자",
    "파스타",
    "리조또",
    "사이드",
    "음료/주류",
  ];

  const handleCategoryClick = (category) => {
    scroller.scrollTo(category, {
      duration: 800,
      delay: 0,
      smooth: "easeInOutQuart",
      containerId: "scrollable-dish-list",
    });
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        height: "100%",
      }}
    >
      <DishSearchBar id="dish-search-bar" />
      <DishTab
        id="dish-tab"
        dishCategories={categories}
        onTabClick={handleCategoryClick}
      />
      <Box
        id="scrollable-dish-list"
        sx={{
          flex: 1,
          py: 1,
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
        {dishes.map((dishCategory, index) => (
          <Element name={dishCategory.categoryName} key={index}>
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
                {dishCategory.categoryName}
              </Typography>
              {dishCategory.items.map((dish, dishIndex) => (
                <Box key={dish.dishId}>
                  <DishItemCard dish={dish} />
                  {dishIndex < dishCategory.items.length - 1 && (
                    <Divider variant="middle" />
                  )}
                </Box>
              ))}
            </Stack>
          </Element>
        ))}
      </Box>
    </Box>
  );
}
