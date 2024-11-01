import { Box, Stack, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import DishTagChip from "../chip/DishTagChip";

const DishItemCard = ({ dish, sx }) => {
  return (
    <Stack
      direction="row"
      spacing={2}
      sx={{
        padding: "10px",
        borderRadius: "10px",
        justifyContent: "space-between",
        alignItems: "center",
        "&:hover": {
          backgroundColor: colors.background.primary,
        },
        transition: "all 0.3s ease", // transition 적용
        cursor: "pointer",
        color: colors.text.main,
      }}
    >
      <Stack>
        <Stack
          direction="row"
          sx={{
            display: "flex",
            flexWrap: "wrap",
          }}
        >
          <Typography
            sx={{
              fontSize: "18px",
              pr: 1,
              fontWeight: "550",
            }}
          >
            {dish.dishName}
          </Typography>
          <Box>
            {dish.tags.length > 0 &&
              dish.tags.map((tag, index) => (
                <DishTagChip key={index} label={tag} />
              ))}
          </Box>
        </Stack>
        <Typography
          sx={{
            fontSize: "12px",
            color: colors.text.sub2,
          }}
        >
          {dish.description}
        </Typography>
        <Typography
          sx={{
            fontSize: "16px",
          }}
        >
          {dish.price.toLocaleString()}원
        </Typography>
      </Stack>
      <Box
        component="img"
        alt={dish.dishName}
        src="https://cdn.dominos.co.kr/admin/upload/goods/20230619_F33836Pn.jpg"
        sx={{
          width: 120,
          height: 120,
          borderRadius: "8px", // 곡률 조절
          objectFit: "cover",
        }}
      />
    </Stack>
  );
};

export default DishItemCard;
