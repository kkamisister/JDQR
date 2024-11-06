import { Box, IconButton, Stack, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import DishTagChip from "../chip/DishTagChip";
import CancelIcon from "@mui/icons-material/Cancel";

const DishItemCard = ({
  dish,
  onClick,
  onClose,
  hasImage = true,
  hasOption = false,
  children,
  sx,
}) => {
  return (
    <Stack>
      <Stack
        onClick={onClick}
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
              {dish.tags &&
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
          {hasOption && (
            <Typography
              sx={{
                fontSize: "12px",
                color: colors.text.sub2,
              }}
            >
              치즈추가, 씬도우로 변경
            </Typography>
          )}

          <Typography
            sx={{
              fontSize: "16px",
            }}
          >
            {dish.price.toLocaleString()}원
          </Typography>
        </Stack>
        {hasImage && (
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
        )}
        {onClose && (
          <IconButton onClick={onClose}>
            <CancelIcon
              sx={{
                color: colors.text.sub2,
              }}
            />
          </IconButton>
        )}
      </Stack>
      {children}
    </Stack>
  );
};

export default DishItemCard;
