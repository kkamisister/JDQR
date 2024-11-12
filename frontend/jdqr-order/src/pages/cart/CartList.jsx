import { useEffect, useState } from "react";
import { Stack, Typography, Button, Box, Divider } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { colors } from "../../constants/colors";
import DishItemCard from "../../components/card/DishItemCard";
import { useNavigate } from "react-router-dom";
import NumberSelector from "../../components/selector/NumberSelector";
import { useSnackbar } from "notistack";
import useWebSocketStore from "../../stores/SocketStore";

export default function CartList() {
  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [dishes, setDishes] = useState([]);
  const { client, connect } = useWebSocketStore();
  const tableId = localStorage.getItem("tableId");

  useEffect(() => {
    if (!client) {
      connect();
    }
  }, [client, connect]);
  useEffect(() => {
    console.log("현재 상품", dishes);
  }, [dishes]);
  useEffect(() => {
    if (client && client.connected) {
      console.log(
        "아~~웹소켓 연결은 제대로 됐다니까?/sub/cart/updates 한번..가볼게"
      );
      const subscription = client.subscribe(
        "/sub/cart/" + tableId,
        (message) => {
          console.log("받은메세지:", message.body);
          const data = JSON.parse(message.body);

          setDishes(data.cartList);

          enqueueSnackbar(`${"아이템"}이 장바구니에 추가되었습니다.`, {
            variant: "success",
          });
        }
      );

      return () => {
        subscription.unsubscribe();
      };
    }
  }, [client, enqueueSnackbar]);

  const onClose = (dishId) => {
    setDishes((prevDishes) => {
      const updatedDishes = prevDishes.filter((_, index) => index !== dishId);
      return updatedDishes;
    });

    enqueueSnackbar(`메뉴가 장바구니에서 삭제되었습니다.`, {
      variant: "error",
    });
  };
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
              <DishItemCard
                dish={dish}
                onClose={() => onClose(dishId)}
                hasImage={false}
                hasOption={true}
              >
                <Stack
                  direction="row"
                  spacing={1}
                  sx={{ alignSelf: "end", px: 2, pb: 1 }}
                >
                  <Button
                    variant="disabled"
                    sx={{
                      bgcolor: colors.background.box,
                      borderRadius: 2,
                      fontSize: "12px",

                      height: "25px",
                      p: 0,
                    }}
                  >
                    옵션 변경
                  </Button>
                  <NumberSelector
                    value={dish.quantity}
                    sx={{ width: "70px" }}
                  />
                </Stack>
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
