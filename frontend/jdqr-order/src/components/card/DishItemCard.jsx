import { Avatar, Box, IconButton, Stack, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import DishTagChip from "../chip/DishTagChip";
import CancelIcon from "@mui/icons-material/Cancel";
import { useState } from "react";

const DishItemCard = ({
  dish,
  onClick,
  onClose,
  hasImage = true,
  hasOption = false,
  children,
  isSelected,
  sx,
}) => {
  return (
    <Stack
      onClick={onClick}
      sx={{
        ...sx,
        backgroundColor: isSelected ? colors.main.primary100 : "transparent",
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
              {dish.choiceNames?.length > 0
                ? dish.choiceNames.map((choice) => choice)
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
