import { Avatar, Box, IconButton, Stack, Typography } from "@mui/material";
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
    <Stack
      sx={{
        ...sx,
        "&:hover": {
          backgroundColor: colors.background.primary,
        },
        padding: "10px",
        borderRadius: "10px",
        transition: "all 0.3s ease", // transition 적용
        cursor: "pointer",
        color: colors.text.main,
      }}
    >
      <Stack
        onClick={onClick}
        direction="row"
        spacing={2}
        sx={{
          justifyContent: "space-between",
          alignItems: "center",
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
              {dish.options?.length > 0
                ? dish.options.map((option, index) => option.choiceName)
                : "\u00A0"}
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
          <Avatar
            alt={dish.dishName}
            src={dish.image}
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
