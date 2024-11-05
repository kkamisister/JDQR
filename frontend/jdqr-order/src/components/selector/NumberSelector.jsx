import { IconButton, TextField, Stack } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";

export default function NumberSelector({ value, setValue, sx }) {
  const handleIncrease = () => setValue(value + 1);
  const handleDecrease = () => setValue(value > 0 ? value - 1 : 0);

  return (
    <Stack direction="row" alignItems="center" spacing={1} sx={{ ...sx }}>
      <IconButton onClick={handleDecrease}>
        <RemoveIcon />
      </IconButton>
      <TextField
        type="number"
        value={value}
        onChange={(e) => setValue(Number(e.target.value))}
        inputProps={{ min: 0 }}
        sx={{ width: 60 }}
      />
      <IconButton onClick={handleIncrease}>
        <AddIcon />
      </IconButton>
    </Stack>
  );
}
