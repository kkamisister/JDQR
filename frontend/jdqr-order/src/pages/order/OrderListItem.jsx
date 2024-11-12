import { Divider, Stack, Typography } from "@mui/material";

export default function OrderListItem() {
  return (
    <Stack>
      <Typography
        sx={{
          fontWeight: 600,
          fontSize: 24,
        }}
      >
        주문
      </Typography>
      <Divider variant="middle" />
    </Stack>
  );
}
