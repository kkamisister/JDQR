import { Stack, FormControlLabel, Checkbox, Typography } from "@mui/material";
import { useState, useEffect } from "react";
import { colors } from "../../constants/colors";
import MenuDivideListItem from "./MenuDivideListItem";
import QuantitySelectDialog from "../../components/dialog/QuantitySelectDialog";

export default function MenuDivideList({ orders }) {
  const userId = sessionStorage.getItem("userId");
  const allDishes = orders.orders.flatMap((order) => order.dishes);
  const [showOnlyMine, setShowOnlyMine] = useState(false);
  const [selectedKeys, setSelectedKeys] = useState([]); // 선택된 key 관리

  const filteredDishes = showOnlyMine
    ? allDishes.filter((dish) => dish.userId === userId)
    : allDishes;

  // 클릭 핸들러
  const handleDishClick = (key) => {
    setSelectedKeys((prev) =>
      prev.includes(key) ? prev.filter((k) => k !== key) : [...prev, key]
    );
  };

  return (
    <Stack>
      <FormControlLabel
        control={
          <Checkbox
            checked={showOnlyMine}
            onChange={(e) => setShowOnlyMine(e.target.checked)}
            sx={{
              color: colors.main.primary500,
              "&.Mui-checked": {
                color: colors.main.primary500,
              },
            }}
          />
        }
        label="내가 시킨 메뉴만 보기"
        sx={{ alignSelf: "flex-end", mb: 2 }}
      />

      {filteredDishes.map((dish) => {
        const key = dish.orderItemId;
        const description = dish.options
          ?.map((option) => option.choiceName)
          .join(", ") || <>&nbsp;</>;
        return (
          <MenuDivideListItem
            key={dish.orderItemId}
            dish={dish}
            description={description}
            isSelected={selectedKeys.includes(key)} // 선택 여부 전달
            onClick={() => handleDishClick(key)} // 클릭 핸들러
          />
        );
      })}
    </Stack>
  );
}
