import {
  Stack,
  FormControlLabel,
  Checkbox,
  Divider,
  Typography,
} from "@mui/material";
import DishItemCard from "../../components/card/DishItemCard";
import { useState } from "react";
import { colors } from "../../constants/colors";
import MenuDivideListItem from "./MenuDivideListItem";

export default function MenuDivideList({ orders }) {
  console.log(orders.orders);
  const userId = sessionStorage.getItem("userId");
  const allDishes = orders.orders.flatMap((order) => order.dishes);

  const [showOnlyMine, setShowOnlyMine] = useState(false);

  const filteredDishes = showOnlyMine
    ? allDishes.filter((dish) => dish.userId === userId)
    : allDishes;

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
        const description = dish.options
          ?.map((option) => option.choiceName)
          .join(", ");
        return (
          <MenuDivideListItem
            key={`${dish.dishId}-${dish.userId}-${dish.options
              ?.map((option) => `${option.optionId}-${option.choiceId}`)
              .sort() //
              .join("_")}`}
            dish={dish}
            description={description}
          />
        );
      })}
    </Stack>
  );
}
