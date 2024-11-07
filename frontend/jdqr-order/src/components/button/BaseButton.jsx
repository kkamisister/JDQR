import { Box, Button, Typography, Stack } from "@mui/material";
import { colors } from "../../constants/colors";

export default function BaseButton({ count, onClick, children }) {
  const mockData = {
    orderId: 1,
    tableName: "영표의 식탁",
    dishCnt: 20, // 담은 음식의 총 개수
    price: 202600, // 담은 음식의 총 가격
    orders: [
      {
        dishId: 4,
        userId: "d8ba9920-08f6-4f65-b7df-811ae20e70d1",
        dishName: "더블쿼터파운드치즈버거",
        dishCategoryId: 1,
        dishCategoryName: "햄버거",
        dishPrice: 12800, // 옵션 가격이 포함되지 않은 메뉴 가격
        options: [],
        quantity: 14,
      },
      {
        dishId: 4,
        userId: "57c7fd62-8c66-4a62-9f12-e6b07e1c40d2",
        dishName: "더블쿼터파운드치즈버거",
        dishCategoryId: 1,
        dishCategoryName: "햄버거",
        dishPrice: 12800, // 옵션 가격이 포함되지 않은 메뉴 가격
        options: [
          {
            optionId: 1,
            optionName: "사이드 메뉴",
            choiceId: 1,
            choiceName: "치즈스틱",
            price: 500, // 해당 옵션의 가격
          },
        ],
        quantity: 6,
      },
    ],
  };

  return (
    <Button
      onClick={onClick}
      sx={{
        position: "fixed",
        bottom: 20,
        left: 0,
        right: 0,
        marginX: "auto",
        bgcolor: colors.main.primary500,
        borderRadius: "10px",
        height: "45px",
        width: "80%",
      }}
    >
      <Stack direction="row" alignItems="center" mx={2}>
        <Box
          sx={{
            bgcolor: colors.background.white,
            color: colors.main.primary500,
            fontSize: 18,
            borderRadius: 1,
            minWidth: 30,
          }}
        >
          {count}
        </Box>
        <Typography
          sx={{
            color: colors.text.white,
            px: 1,
          }}
        >
          {children}
        </Typography>
      </Stack>
    </Button>
  );
}
