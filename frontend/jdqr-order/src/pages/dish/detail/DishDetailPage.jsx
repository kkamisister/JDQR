import { Stack, Typography, Box, Divider } from "@mui/material";
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader";
import DishItemCard from "../../../components/card/DishItemCard";
import { colors } from "../../../constants/colors";
import DishTagChip from "../../../components/chip/DishTagChip";
import DishOptions from "./DishOptions";

export default function DishDetailPage() {
  const mockDish = {
    status: 200,
    message: "메뉴 조회에 성공하였습니다.",
    data: {
      dishId: 1,
      dishName: "핫치킨 피자",
      price: 12800,
      description: "불닭볶음면보다 매운 피자🔥",
      image: "https://example.com/image1.jpg",
      options: [
        {
          optionId: 1,
          optionName: "도우 변경",
          choices: [
            {
              choiceId: 1,
              choiceName: "치즈 추가",
              price: 2000,
            },
            {
              choiceId: 2,
              choiceName: "고구마 무스 추가",
              price: 2000,
            },
            {
              choiceId: 3,
              choiceName: "치즈 크러스트로 변경",
              price: 4000,
            },
            {
              choiceId: 4,
              choiceName: "골드 크러스트로 변경",
              price: 5000,
            },
          ],
        },
      ],
      tags: ["인기"],
    },
  };

  return (
    <Stack>
      <MapBackButtonHeader />
      <Box
        component="img"
        src="https://cdn.dominos.co.kr/admin/upload/goods/20230619_F33836Pn.jpg"
        sx={{
          objectFit: "cover",
          maxHeight: "200px",
        }}
      />

      {/* 메뉴명 && 태그 */}
      <Stack direction="row" alignItems="center" spacing={1}>
        <Typography
          sx={{ fontSize: 24, fontWeight: 600, color: colors.text.main }}
        >
          {mockDish.data.dishName}
        </Typography>
        <>
          {mockDish.data.tags.map((tag, index) => (
            <DishTagChip label={tag} key={index} />
          ))}
        </>
      </Stack>

      {/* 설명 */}
      <Typography sx={{ fontSize: 16, color: colors.text.sub2 }}>
        {mockDish.data.description}
      </Typography>

      {/* 가격 */}
      <Stack
        direction="row"
        sx={{
          justifyContent: "space-between",
        }}
      >
        <Typography
          sx={{ fontSize: 20, fontWeight: 600, color: colors.text.main }}
        >
          가격
        </Typography>
        <Stack direction="row" alignItems="baseline">
          <Typography
            sx={{ fontSize: 20, fontWeight: 600, color: colors.text.main }}
          >
            {mockDish.data.price.toLocaleString()}
          </Typography>
          <Typography sx={{ fontSize: 16, color: colors.text.main }}>
            원
          </Typography>
        </Stack>
      </Stack>
      <Divider variant="middle" sx={{}} />

      {/* 옵션 */}
      <DishOptions options={mockDish.data.options} />
    </Stack>
  );
}
