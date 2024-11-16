import { useEffect, useState } from "react";
import { Element, scroller } from "react-scroll";
import DishSearchBar from "./DishSearchBar";
import { Box, Divider, Stack, Typography } from "@mui/material";
import DishTab from "../../components/tab/DishTab";
import DishItemCard from "../../components/card/DishItemCard";
import { colors } from "../../constants/colors";
import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";

export default function DishList({ dishes, categories }) {
  const navigate = useNavigate();

  const handleCategoryClick = (category) => {
    // console.log("카테고리는 말이죵", category);
    scroller.scrollTo(category, {
      duration: 800,
      delay: 0,
      smooth: "easeInOutQuart",
      containerId: "scrollable-dish-list",
    });
  };

  const handleDishClick = (dishId) => {
    navigate(`${dishId}`);
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        height: "100%",
      }}
    >
      <DishSearchBar />
      <DishTab dishCategories={categories} onTabClick={handleCategoryClick} />

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
          "-webkit-overflow-scrolling": "touch",
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
                {category.dishCategoryName}
              </Typography>
              {category.items.map((dish, dishIndex) => (
                <Box key={dish.dishId}>
                  <DishItemCard
                    dish={dish}
                    onClick={() => handleDishClick(dish.dishId)}
                  />
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
  );
}
