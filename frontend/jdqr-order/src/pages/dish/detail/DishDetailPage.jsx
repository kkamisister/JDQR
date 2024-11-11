import { useParams } from "react-router-dom";
import { Stack, Typography, Box, Divider } from "@mui/material";
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader";
import { colors } from "../../../constants/colors";
import DishTagChip from "../../../components/chip/DishTagChip";
import DishOptions from "./DishOptions";
import { fetchDishDetail } from "../../../utils/apis/dish";
import { useQuery } from "@tanstack/react-query";
import LoadingSpinner from "../../../components/Spinner/LoadingSpinner";
import { useState, useMemo } from "react";
import NumberSelector from "../../../components/selector/NumberSelector";
import BaseButton from "../../../components/button/BaseButton";

export default function DishDetailPage() {
  const { dishId } = useParams();
  const parsedDishId = Number(dishId);
  const [selectedOptions, setSelectedOptions] = useState({});
  const [quantity, setQuantity] = useState(1);

  const { data, isLoading, isError } = useQuery({
    queryKey: ["dishDetail", parsedDishId],
    queryFn: () => fetchDishDetail(parsedDishId),
    enabled: !isNaN(parsedDishId),
  });

  const handleOptionChange = (optionId, choiceId) => {
    setSelectedOptions((prev) => ({
      ...prev,
      [optionId]: choiceId,
    }));
  };

  const handleQuantityChange = (newQuantity) => {
    setQuantity(newQuantity);
  };

  const selectedOptionsTotalPrice = useMemo(() => {
    return Object.values(selectedOptions).reduce((sum, choiceId) => {
      const choice = data.options
        ?.flatMap((option) => option.choices)
        .find((c) => c.choiceId === choiceId);
      return sum + (choice ? choice.price : 0);
    }, 0);
  }, [selectedOptions, data.options]);

  const totalSum = useMemo(() => {
    const basePrice = data.price || 0;
    return (basePrice + selectedOptionsTotalPrice) * quantity;
  }, [data.price, selectedOptionsTotalPrice, quantity]);

  return (
    <>
      {isLoading ? (
        <LoadingSpinner message={"메뉴 정보 가져오는 중"} />
      ) : (
        !isError && (
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
                  {data.tags.map((tag, index) => (
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
            <BaseButton count={quantity} onClick={() => console.log("잘됨")}>
              {totalSum.toLocaleString()}원 장바구니 담기
            </BaseButton>
          </>
        )
      )}
    </>
  );
}
