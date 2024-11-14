import { useEffect, useState } from "react";
import { Stack, Typography, Button, Box, Divider } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { colors } from "../../constants/colors";
import DishItemCard from "../../components/card/DishItemCard";
import { useNavigate } from "react-router-dom";
import NumberSelector from "../../components/selector/NumberSelector";
import { useSnackbar } from "notistack";
import useWebSocketStore from "../../stores/SocketStore";
import CartListItem from "./CartListItem";
import BaseButton from "../../components/button/BaseButton";
import Footer from "../../components/footer/Footer";

export default function CartList() {
  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [dishes, setDishes] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [totalQuantity, setTotalQuantity] = useState(0);
  const { client, connect } = useWebSocketStore();
  const tableId = sessionStorage.getItem("tableId");
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
    if (!client) {
      connect();
    }
  }, [client, connect]);

  useEffect(() => {
    if (client && client.connected) {
      const subscription = client.subscribe(
        "/sub/cart/" + tableId,
        (message) => {
          const data = JSON.parse(message.body);
          console.log("내가 받은데이따", data.cartList);
          setDishes(data.cartList);
          setTotalPrice(data.totalPrice);
          setTotalQuantity(data.totalQuantity);
        }
      );

      return () => {
        subscription.unsubscribe();
      };
    }
  }, [client]);

  const myDishes = dishes.filter((dish) => dish.userId === userId);
  const othersDishes = dishes.filter((dish) => dish.userId !== userId);

  const goToDish = () => {
    navigate("/dish");
  };

  const goToPayment = () => {
    navigate("/payment");
  };

  return (
    <Stack
      spacing={5}
      sx={{
        bgcolor: colors.background.white,
        mt: 2,
      }}
    >
      <CartListItem title="내가 담은 메뉴" dishes={myDishes} />
      <CartListItem title="일행이 담은 메뉴" dishes={othersDishes} />
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
      <Footer />

      <Box>
        {totalQuantity > 0 && totalPrice && (
          <BaseButton count={totalQuantity} onClick={goToPayment}>
            {`${totalPrice.toLocaleString()}원 결제하기`}
          </BaseButton>
        )}
      </Box>
    </Stack>
  );
}
