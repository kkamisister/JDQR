import { Box, Button, Typography, Stack } from "@mui/material";
import { colors } from "../../constants/colors";

export default function BaseButton({ count, onClick, children }) {
  return (
    <Stack
      sx={{
        position: "sticky",
        bottom: 20,
        alignItems: "center",
      }}
    >
      <Button
        onClick={onClick}
        sx={{
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
    </Stack>
  );
}
