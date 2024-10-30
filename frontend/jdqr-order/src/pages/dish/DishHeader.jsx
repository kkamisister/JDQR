import { Stack, Button, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import ReceiptLongIcon from "@mui/icons-material/ReceiptLong";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";

export default function DishHeader() {
  return (
    <Stack
      spacing={2}
      px={4}
      py={2}
      sx={{
        bgcolor: colors.main.primary100,
      }}
    >
      <Stack direction="row" justifyContent="space-between">
        <Button
          startIcon={<ReceiptLongIcon />}
          sx={{
            bgcolor: colors.text.sub1,
            color: colors.text.white,
            fontWeight: 600,
            width: "45%",
            py: 1.5,
          }}
        >
          {`주문 내역 ${0}건`}
        </Button>
        <Button
          startIcon={<ShoppingCartIcon />}
          sx={{
            bgcolor: colors.main.primary500,
            color: colors.text.white,
            fontWeight: 600,
            width: "45%",
            py: 1.5,
          }}
        >
          {`장바구니 ${12}건`}
        </Button>
      </Stack>
      <Button
        sx={{
          bgcolor: colors.background.white,
          color: colors.text.sub1,
          border: "solid",
          borderColor: colors.text.sub,
          p: 1,
        }}
      >
        <Typography fontWeight="bold">{`${7}명`}</Typography>이 함께 주문하고
        있어요!
      </Button>
    </Stack>
  );
}
