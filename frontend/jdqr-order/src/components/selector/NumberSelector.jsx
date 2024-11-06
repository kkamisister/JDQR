import { Box, IconButton, Stack, Typography } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { colors } from "../../constants/colors";
import { useState } from "react";

export default function NumberSelector({ value = 1, fontSize, sx }) {
  const [selectorValue, setValue] = useState(value);
  const handleIncrease = () => setValue(selectorValue + 1);
  const handleDecrease = () =>
    setValue(selectorValue > 1 ? selectorValue - 1 : 1);

  return (
    <Stack
      direction="row"
      alignItems="center"
      justifyContent="space-evenly"
      sx={{
        ...sx,
        bgcolor: sx?.bgcolor || colors.background.box,
        width: sx?.width || "90px",
        borderRadius: 2,
        height: sx?.height || "25px",
      }}
    >
      <IconButton
        onClick={handleDecrease}
        sx={{ p: 0, color: colors.text.sub1 }}
      >
        <RemoveIcon sx={{ width: "16px" }} />
      </IconButton>

      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          color: colors.text.main,
          bgcolor: colors.background.white,
          width: "40%",
          borderRadius: 2,
          height: "80%",
          fontSize: fontSize || "12px",
        }}
      >
        {selectorValue}
      </Box>
      <IconButton
        onClick={handleIncrease}
        sx={{ p: 0, color: colors.text.sub1 }}
      >
        <AddIcon sx={{ width: "16px" }} />
      </IconButton>
    </Stack>
  );
}
