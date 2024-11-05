import { Box, IconButton, Stack, Typography } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { colors } from "../../constants/colors";

export default function NumberSelector({ value = 1, setValue, sx }) {
  const handleIncrease = () => setValue(value + 1);
  const handleDecrease = () => setValue(value > 1 ? value - 1 : 1);

  return (
    <Stack
      direction="row"
      alignItems="center"
      justifyContent="center"
      sx={{
        ...sx,
        bgcolor: sx?.bgcolor || colors.background.box,
        width: sx?.width || "90px",
        borderRadius: 2,
        py: 0.5,
      }}
    >
      <IconButton
        onClick={handleDecrease}
        sx={{ p: 0, color: colors.text.sub1 }}
      >
        <RemoveIcon />
      </IconButton>

      <Typography
        sx={{
          color: colors.text.main,
          bgcolor: colors.background.white,
          width: "40%",
          borderRadius: 2,
          textAlign: "center",
        }}
      >
        {value}
      </Typography>
      <IconButton
        onClick={handleIncrease}
        sx={{ p: 0, color: colors.text.sub1 }}
      >
        <AddIcon />
      </IconButton>
    </Stack>
  );
}
