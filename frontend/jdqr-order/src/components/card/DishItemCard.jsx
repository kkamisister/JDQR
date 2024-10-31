import React from "react";
import { Box, Stack, Avatar } from "@mui/material";
import { colors } from "constants/colors";

const DishItemCard = ({
  dishId,
  dishName,
  price,
  description,
  imageUrl,
  tags,
  children,
  sx,
}) => {
  return (
    <Stack
      direction="row"
      spacing={2}
      sx={{
        backgroundColor: colors.background.box,
        padding: "10px",
        borderRadius: "10px",
        justifyContent: "space-between",
        alignItems: "center",
        width: "100%",
        "&:hover": {
          backgroundColor: colors.point.stroke,
        },
        transition: "all 0.3s ease", // transition 적용
        cursor: "pointer",
        ...sx,
      }}
    >
      <Stack
        direction="row"
        spacing={1}
        sx={{
          justifyContent: "space-between",
          alignItems: "center",
          fontSize: "14px",
        }}
      >
        <Avatar
          alt={dishName}
          src="//images.ctfassets.net/j8tkpy1gjhi5/5OvVmigx6VIUsyoKz1EHUs/b8173b7dcfbd6da341ce11bcebfa86ea/Salami-pizza-hero.jpg?w=576&fm=webp&q=80"
        />
        <Stack
          spacing={0.5}
          sx={{
            justifyContent: "center",
            alignItems: "flex-start",
          }}
        >
          <Box sx={{ fontWeight: "bold" }}>{dishName}</Box>
          <Box
            sx={{
              color: colors.text.sub2,
            }}
          >
            {description}
          </Box>
        </Stack>
      </Stack>
      {children}
    </Stack>
  );
};

export default DishItemCard;
