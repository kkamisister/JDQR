import { useEffect, useState } from "react";
import { Element, scroller } from "react-scroll";
import DishSearchBar from "./DishSearchBar";
import { Box, Divider, Stack, Typography } from "@mui/material";
import DishTab from "../../components/tab/DishTab";
import DishItemCard from "../../components/card/DishItemCard";
import { colors } from "../../constants/colors";
import { useNavigate } from "react-router-dom";

export default function DishList({ dishes }) {
  const navigate = useNavigate();

  const mockDish = {
    status: 200,
    message: "메뉴 조회에 성공하였습니다.",
    data: {
      dishId: 1,
      dishName: "핫치킨 피자",
      price: 12800,
      description: "불닭볶음면보다 매운 피자🔥",
      imageUrl: "https://example.com/image1.jpg",
      options: [
        {
          optionId: 1,
          optionName: "도우 변경",
          choices: [
            {
              choiceId: 1,
              choiceName: "치즈 추가",
              price: 2000,
            },
            {
              choiceId: 2,
              choiceName: "고구마 무스 추가",
              price: 2000,
            },
            {
              choiceId: 3,
              choiceName: "치즈 크러스트로 변경",
              price: 4000,
            },
            {
              choiceId: 4,
              choiceName: "골드 크러스트로 변경",
              price: 5000,
            },
          ],
        },
      ],
      tags: ["인기"],
    },
  };

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

  // 아이템을 세션 스토리지에 저장하고 리다이렉트하는 함수
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
                  <DishItemCard
                    dish={dish}
                    onClick={() => handleDishClick(dish.dishId)}
                  />
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
