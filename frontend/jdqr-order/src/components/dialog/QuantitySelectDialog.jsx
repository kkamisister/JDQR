import { useState } from "react";
import { colors } from "../../constants/colors";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  TextField,
} from "@mui/material";

export default function QuantitySelectDialog({
  open,
  onClose,
  maxQuantity,
  onSelectQuantity,
}) {
  const [selectedQuantity, setSelectedQuantity] = useState(1);

  const handleQuantityChange = (event) => {
    const value = Math.min(
      maxQuantity,
      Math.max(1, Number(event.target.value))
    );
    setSelectedQuantity(value);
  };

  const handleConfirm = () => {
    onSelectQuantity(selectedQuantity);
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth>
      <DialogTitle>수량 선택</DialogTitle>
      <DialogContent>
        <Typography>결제 수량:</Typography>
        <TextField
          type="number"
          value={selectedQuantity}
          onChange={handleQuantityChange}
          inputProps={{ min: 1, max: maxQuantity }}
          fullWidth
        />
      </DialogContent>
      <DialogActions>
        <Button
          onClick={onClose}
          sx={{
            color: colors.text.main,
            bgcolor: colors.background.white,
            ":hover": {
              color: colors.text.white,
              bgcolor: colors.main.primary500,
            },
          }}
        >
          취소
        </Button>
        <Button
          onClick={handleConfirm}
          sx={{ color: colors.text.white, bgcolor: colors.main.primary500 }}
        >
          확인
        </Button>
      </DialogActions>
    </Dialog>
  );
}
