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
  const [orderCnt, setOrderCnt] = useState(20);
  let tableId = sessionStorage.getItem("tableId");

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
    if (client && client.connected) {
      console.log("일단 웹소켓 연결은 됨;;");
      const subscription = client.subscribe("sub/cart" + tableId, (message) => {
        console.log("이것이 멧쉐지", message.body);
      });
    }
  });

  useEffect(() => {
    if (data) {
      setOrderCnt(data.orders.length);
    }
  }, [data]);

  const goToCart = () => {
    navigate("/cart");
  };

  const { data, isLoading } = useQuery({
    queryKey: ["orderList"],
    queryFn: fetchOrderList,
  });

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
          {`장바구니 ${12}건`}
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
        <Typography
          color={colors.text.sub1}
          fontWeight="bold"
        >{`${7}명`}</Typography>
        <Typography color={colors.text.sub1}>
          이 함께 주문하고 있어요!
        </Typography>
      </Button>
    </Stack>
  );
}
