import { useNavigate, useParams } from "react-router-dom";
import { Stack, Typography, Box, Divider } from "@mui/material";
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader";
import { colors } from "../../../constants/colors";
import DishTagChip from "../../../components/chip/DishTagChip";
import DishOptions from "./DishOptions";
import { fetchDishDetail } from "../../../utils/apis/dish";
import { useQuery } from "@tanstack/react-query";
import LoadingSpinner from "../../../components/Spinner/LoadingSpinner";
import { useState, useMemo, useEffect } from "react";
import NumberSelector from "../../../components/selector/NumberSelector";
import BaseButton from "../../../components/button/BaseButton";
import { Stomp } from "@stomp/stompjs";
import { setUserCookie } from "../../../utils/apis/axiosInstance";
import useWebSocketStore from "../../../stores/SocketStore";

export default function DishDetailPage() {
  const navigate = useNavigate();
  const { dishId } = useParams();
  const parsedDishId = Number(dishId);
  const [selectedOptions, setSelectedOptions] = useState({});
  const [quantity, setQuantity] = useState(1);

  const { data, isLoading, isError } = useQuery({
    queryKey: ["dishDetail", parsedDishId],
    queryFn: () => fetchDishDetail(parsedDishId),
    enabled: !isNaN(parsedDishId),
  });

  const { client, connect } = useWebSocketStore();

  useEffect(() => {
    if (!sessionStorage.getItem("userId")) {
      setUserCookie();
    }
    if (!client) {
      connect();
    }
  }, [client, connect]);

  const handleOptionChange = (optionId, choiceId) => {
    setSelectedOptions((prev) => {
      if (prev[optionId] === choiceId) {
        const updatedOptions = { ...prev };
        delete updatedOptions[optionId];
        return updatedOptions;
      }

      return {
        ...prev,
        [optionId]: choiceId,
      };
    });
  };

  const handleQuantityChange = (newQuantity) => {
    setQuantity(newQuantity);
  };

  const selectedOptionsTotalPrice = useMemo(() => {
    // data와 data.options가 없으면 0 반환
    if (!data || !data.options) return 0;

    return Object.values(selectedOptions).reduce((sum, choiceId) => {
      const choice = data.options
        .flatMap((option) => option.choices)
        .find((c) => c.choiceId === choiceId);
      return sum + (choice ? choice.price : 0);
    }, 0);
  }, [selectedOptions, data]);

  const totalSum = useMemo(() => {
    if (!data) return 0; // data가 없으면 0 반환
    const basePrice = data.price || 0;

    // data.options가 없는 경우 빈 배열로 처리
    const options = data.options || [];

    if (options.length === 0) {
      return basePrice * quantity;
    }

    return (basePrice + selectedOptionsTotalPrice) * quantity;
  }, [data, selectedOptionsTotalPrice, quantity]);

  const handleAddToCart = () => {
    if (client && client.connected) {
      const orderedAt = new Date().getTime();

      console.log("주문시각은요.", orderedAt);

      const postData = {
        userId: sessionStorage.getItem("userId"),
        dishId: parsedDishId,
        choiceIds: Object.values(selectedOptions),
        price: data.price,
        quantity,
        orderedAt,
      };
      console.log(postData);
      client.send("/pub/cart/add", {}, JSON.stringify(postData));
      console.log(`${data.dishName} 장바구니에 담기 요청 전송`);
      navigate("/cart");
    } else {
      console.error("STOMP 클라이언트가 연결되지 않았습니다.");
    }
  };

  return (
    <>
      {isLoading ? (
        <LoadingSpinner message={"메뉴 정보 가져오는 중"} />
      ) : (
        !isError &&
        data && (
          <>
            {/* 이미지 && 뒤로가기 버튼 */}
            <Stack>
              <Box
                component="img"
                src="https://cdn.dominos.co.kr/admin/upload/goods/20230619_F33836Pn.jpg"
                sx={{
                  objectFit: "cover",
                  maxHeight: "200px",
                }}
              />
              <MapBackButtonHeader />
            </Stack>

            <Stack spacing={1} p={2}>
              {/* 메뉴명 && 태그 */}
              <Stack direction="row" alignItems="center" spacing={1}>
                <Typography
                  sx={{
                    fontSize: 24,
                    fontWeight: 600,
                    color: colors.text.main,
                  }}
                >
                  {data.dishName}
                </Typography>
                <>
                  {data.tags?.length > 0 &&
                    data.tags.map((tag, index) => (
                      <DishTagChip label={tag} key={index} />
                    ))}
                </>
              </Stack>

              {/* 설명 */}
              <Typography sx={{ fontSize: 16, color: colors.text.sub2 }}>
                {data.description}
              </Typography>

              {/* 가격 */}
              <Stack
                direction="row"
                sx={{
                  justifyContent: "space-between",
                }}
              >
                <Typography
                  sx={{
                    fontSize: 20,
                    fontWeight: 600,
                    color: colors.text.main,
                  }}
                >
                  가격
                </Typography>
                <Stack direction="row" alignItems="baseline">
                  <Typography
                    sx={{
                      fontSize: 20,
                      fontWeight: 600,
                      color: colors.text.main,
                    }}
                  >
                    {data.price.toLocaleString()}
                  </Typography>
                  <Typography sx={{ fontSize: 16, color: colors.text.main }}>
                    원
                  </Typography>
                </Stack>
              </Stack>
            </Stack>

            {/* 옵션 */}
            {data.options?.length > 0 && (
              <Box p={2}>
                <Divider
                  variant="middle"
                  sx={{ borderWidth: "1.5px", mb: 2 }}
                />
                <DishOptions
                  options={data.options}
                  selectedOptions={selectedOptions}
                  handleOptionChange={handleOptionChange}
                />
              </Box>
            )}

            {/* 수량 */}
            <Box p={2}>
              <Divider variant="middle" sx={{ borderWidth: "1.5px", mb: 2 }} />
              <Typography
                sx={{
                  fontSize: 20,
                  fontWeight: 600,
                  color: colors.text.main,
                }}
              >
                수량
              </Typography>
              <Stack
                direction="row"
                justifySelf="end"
                sx={{
                  fontSize: 20,
                  alignItems: "center",
                }}
              >
                <NumberSelector
                  onChange={handleQuantityChange}
                  fontSize={18}
                  sx={{
                    bgcolor: colors.main.primary100,
                    width: "105px",
                    height: "40px",
                    m: "4px",
                  }}
                />
                개
              </Stack>
            </Box>
            <BaseButton count={quantity} onClick={handleAddToCart}>
              {totalSum.toLocaleString()}원 장바구니 담기
            </BaseButton>
          </>
        )
      )}
    </>
  );
}
