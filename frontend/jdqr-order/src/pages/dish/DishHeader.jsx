import { Stack, Button, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import ReceiptLongIcon from "@mui/icons-material/ReceiptLong";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { useNavigate } from "react-router-dom";
import useWebSocketStore from "../../stores/SocketStore";
import { setUserCookie, initializeToken } from "../../utils/apis/axiosInstance";
import { useEffect, useState } from "react";
import { fetchOrderList } from "../../utils/apis/order";
import { useQuery } from "@tanstack/react-query";

export default function DishHeader() {
  const navigate = useNavigate();
  const { client, connect } = useWebSocketStore();
  const [orderCnt, setOrderCnt] = useState(0);
  const [peopleCnt, setPeopleCnt] = useState();
  const [cartList, setCartList] = useState([]);

  let tableId = sessionStorage.getItem("tableId");
  const goToOrder = () => {
    navigate("/order");
  };

  const goToCart = () => {
    navigate("/cart");
  };

  useEffect(() => {
    if (!sessionStorage.getItem("userId")) {
      setUserCookie();
    }
    if (!client) {
      connect();
    }
  }, [client, connect]);

  useEffect(() => {
    if (!tableId || tableId === "undefined") {
      initializeToken();
      tableId = sessionStorage.getItem("tableId");
    }
    let subscription;
    if (client && client.connected) {
      subscription = client.subscribe("/sub/cart/" + tableId, (message) => {
        const response = JSON.parse(message.body);
        setCartList(response.cartList);
        setPeopleCnt(response.peopleCnt);
      });
    }
    return () => {
      if (subscription) {
        subscription.unsubscribe();
      }
    };
  }, [client]);

  const { data, isLoading } = useQuery({
    queryKey: ["orderList"],
    queryFn: fetchOrderList,
  });

  useEffect(() => {
    if (data?.orders) {
      setOrderCnt(data.orders.length);
      console.log(data.orders);
    } else {
      setOrderCnt(0);
    }
  }, [data]);

  return (
    <Stack
      spacing={2}
      px={4}
      py={2}
      sx={{
        bgcolor: colors.main.primary100,
      }}
    >
      <Stack direction="row" justifyContent="space-between">
        <Button
          onClick={goToOrder}
          startIcon={<ReceiptLongIcon />}
          sx={{
            bgcolor: colors.text.sub1,
            color: colors.text.white,
            fontWeight: 600,
            width: "45%",
            py: 1.5,
          }}
        >
          {`주문 내역 ${orderCnt}건`}
        </Button>

        <Button
          startIcon={<ShoppingCartIcon />}
          onClick={goToCart}
          sx={{
            bgcolor: colors.main.primary500,
            color: colors.text.white,
            fontWeight: 600,
            width: "45%",
            py: 1.5,
          }}
        >
          {`장바구니 ${cartList?.length || 0}건`}
        </Button>
      </Stack>
      <Button
        disabled
        sx={{
          bgcolor: colors.background.white,
          border: "solid",
          borderColor: colors.background.box,
          p: 1,
        }}
      >
        <Typography color={colors.text.sub1} fontWeight="bold">
          {peopleCnt > 1 && <span>총</span>}
          {`${peopleCnt || 1}명`}
        </Typography>
        <Typography color={colors.text.sub1}>
          이 {peopleCnt > 1 && <span>함께</span>} 주문하고 있어요!
        </Typography>
      </Button>
    </Stack>
  );
}
