import { Element, scroller } from "react-scroll";
import DishSearchBar from "./DishSearchBar";
import { Box, Divider } from "@mui/material";
import DishTab from "../../components/tab/DishTab";
import DishItemCard from "../../components/card/DishItemCard";

export default function DishList({ dishes }) {
  const categories = [
    "인기메뉴",
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
      offset: -50, // 상단바 높이에 따라 조정 가능
    });
  };

  return (
    <Box>
      <DishSearchBar />
      <DishTab dishCategories={categories} />
      {dishes.map((dishCategory, index) =>
        dishCategory.items.map((dish, dishIndex) => (
          <>
            <DishItemCard dish={dish} key={`${index}-${dishIndex}`} />
            <Divider variant="middle" />
          </>
        ))
      )}
    </Box>
  );
}
