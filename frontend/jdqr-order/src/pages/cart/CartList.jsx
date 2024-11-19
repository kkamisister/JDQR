import { useEffect, useState } from "react";
import { Stack, Typography, Button, Box, Divider } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { colors } from "../../constants/colors";
import { useNavigate } from "react-router-dom";
import { useSnackbar } from "notistack";
import useWebSocketStore from "../../stores/SocketStore";
import CartListItem from "./CartListItem";
import BaseButton from "../../components/button/BaseButton";
import { placeOrder } from "../../utils/apis/order";
import AddShoppingCartIcon from "@mui/icons-material/AddShoppingCart";

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

  const submitOrder = async () => {
    try {
      placeOrder();
      navigate("/order");
    } catch (error) {
      throw new Error(
        "왤케 조금 시키세요;;;;이러면 자영업자들 뭐 목고 살라고;;"
      );
    }
  };

  return (
    <Stack>
      {myDishes.length === 0 && othersDishes.length === 0 && (
        <Stack
          alignItems="center"
          justifyContent="center"
          sx={{
            textAlign: "center",
            mt: 10,
          }}
        >
          {myDishes.length === 0 && othersDishes.length === 0 && (
            <Stack>
              <AddShoppingCartIcon
                sx={{
                  color: colors.main.primary500,
                  fontSize: 200,
                }}
              />
              <Typography
                sx={{
                  color: colors.text.main,
                  fontWeight: 600,
                  fontSize: 18,
                  mt: 2,
                }}
              >
                아직 담은 메뉴가 없습니다. 메뉴를 추가해보세요!
              </Typography>
            </Stack>
          )}
        </Stack>
      )}
      {myDishes?.length > 0 && (
        <CartListItem title="내가 담은 메뉴" dishes={myDishes} />
      )}
      {othersDishes?.length > 0 && (
        <CartListItem title="일행이 담은 메뉴" dishes={othersDishes} />
      )}
      {/* {(myDishes.length > 0 || othersDishes.length > 0) && ( */}
      <Button
        endIcon={<AddCircleIcon />}
        onClick={goToDish}
        sx={{
          color: colors.main.primary500,
          fontSize: "18px",
          minWidth: "30%",
          alignSelf: "center",
          py: 2,
        }}
      >
        메뉴 더 담기
      </Button>
      {/* )} */}
      <Box>
        {totalQuantity > 0 && totalPrice && (
          <BaseButton count={totalQuantity} onClick={submitOrder}>
            {`${totalPrice.toLocaleString()}원 주문하기`}
          </BaseButton>
        )}
      </Box>
    </Stack>
  );
}
