import { Box, Button, Typography, Stack } from "@mui/material";
import { colors } from "../../constants/colors";

export default function BaseButton({
  count,
  onClick,
  disabled = false,
  children,
}) {
  return (
    <Button
      onClick={onClick}
      disabled={disabled}
      sx={{
        position: "fixed",
        bottom: 20,
        left: 0,
        right: 0,
        marginX: "auto",
        bgcolor: disabled ? colors.background.white : colors.main.primary500,
        border: `1px solid ${
          disabled ? colors.main.primary300 : "transparent"
        }`,
        borderRadius: "10px",
        height: "45px",
        width: "80%",
        color: disabled ? colors.main.primary500 : colors.text.white,
        "& .MuiTypography-root": {
          color: disabled ? colors.main.primary300 : colors.text.white,
        },
        "& .MuiBox-root": {
          bgcolor: colors.background.white,
          color: disabled ? colors.text.white : colors.main.primary500,
        },
      }}
    >
      <Stack direction="row" alignItems="center" mx={2}>
        <Box
          sx={{
            fontSize: 18,
            borderRadius: 1,
            minWidth: 30,
            textAlign: "center",
          }}
        >
          {count}
        </Box>
        <Typography
          sx={{
            px: 1,
          }}
        >
          {children}
        </Typography>
      </Stack>
    </Button>
  );
}
