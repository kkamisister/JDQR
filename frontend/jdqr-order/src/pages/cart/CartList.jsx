import { useEffect, useState } from "react";
import { Stack, Typography, Button, Box, Divider } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { colors } from "../../constants/colors";
import DishItemCard from "../../components/card/DishItemCard";
import { useNavigate } from "react-router-dom";
import NumberSelector from "../../components/selector/NumberSelector";

export default function CartList() {
  const navigate = useNavigate();
  const [dishes, setDishes] = useState([]);
  console.log(dishes);
  useEffect(() => {
    // sessionStorage에서 dishes 데이터 가져오기
    const storedDishes = sessionStorage.getItem("cartList");
    if (storedDishes) {
      try {
        const parsedDishes = JSON.parse(storedDishes);
        setDishes(Array.isArray(parsedDishes) ? parsedDishes : []);
      } catch (error) {
        console.error("JSON 파싱 오류:", error);
        setDishes([]);
      }
    }
  }, []);

  const goToDish = () => {
    navigate("/dish");
  };

  return (
    <Stack
      spacing={5}
      sx={{
        bgcolor: colors.background.white,
        mt: 2,
      }}
    >
      <Stack>
        <Typography
          sx={{
            fontWeight: 600,
            fontSize: "16px",
            p: "10px",
          }}
        >
          담은 메뉴
        </Typography>
        {dishes.length > 0 &&
          dishes.map((dish, dishId) => (
            <Box key={dishId}>
              <DishItemCard dish={dish} hasImage={false} hasOption={true}>
                <NumberSelector />
              </DishItemCard>
              <Divider variant="middle" />
            </Box>
          ))}
      </Stack>
      <Button
        endIcon={<AddCircleIcon />}
        onClick={goToDish}
        sx={{
          color: colors.main.primary500,
          fontSize: "18px",
          width: "30%",
          alignSelf: "center",
        }}
      >
        메뉴 더 담기
      </Button>
    </Stack>
  );
}
