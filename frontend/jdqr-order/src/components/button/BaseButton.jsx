import { Box, Button, Typography, Stack } from "@mui/material";
import { colors } from "../../constants/colors";

export default function BaseButton({ count, onClick, children }) {
  return (
    <Button
      onClick={onClick}
      sx={{
        position: "fixed",
        bottom: 20,
        left: 0,
        right: 0,
        marginX: "auto",
        bgcolor: colors.main.primary500,
        borderRadius: "10px",
        height: "45px",
        width: "80%",
      }}
    >
      <Stack direction="row" alignItems="center" mx={2}>
        <Box
          sx={{
            bgcolor: colors.background.white,
            color: colors.main.primary500,
            fontSize: 18,
            borderRadius: 1,
            minWidth: 30,
          }}
        >
          {count}
        </Box>
        <Typography
          sx={{
            color: colors.text.white,
            px: 1,
          }}
        >
          {children}
        </Typography>
      </Stack>
    </Button>
  );
}
