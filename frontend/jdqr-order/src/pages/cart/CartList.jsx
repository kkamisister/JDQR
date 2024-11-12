import { useEffect, useState } from "react";
import { Stack, Typography, Button, Box, Divider } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { colors } from "../../constants/colors";
import DishItemCard from "../../components/card/DishItemCard";
import { useNavigate } from "react-router-dom";
import NumberSelector from "../../components/selector/NumberSelector";
import { useSnackbar } from "notistack";
import { Stomp } from "@stomp/stompjs";

export default function CartList() {
  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [dishes, setDishes] = useState([]);
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    const client = Stomp.client("wss://jdqr608.duckdns.org/ws");
    client.connect({}, () => {
      console.log("STOMP 연결 성공");
      setStompClient(client);

      // 장바구니 추가 메시지 구독
      client.subscribe("/sub/cart/updates", (message) => {
        const newDish = JSON.parse(message.body);

        // 스낵바 알림 표시
        enqueueSnackbar(`${newDish.dishName}이 장바구니에 추가되었습니다.`, {
          variant: "success",
        });

        // 장바구니 데이터 상태에 새로운 아이템 추가
        setDishes((prevDishes) => [...prevDishes, newDish]);
      });
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect(() => {
          console.log("STOMP 연결 종료");
        });
      }
    };
  }, []);

  const onClose = (dishID) => {
    return;
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
