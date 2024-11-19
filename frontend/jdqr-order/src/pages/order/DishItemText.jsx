import { Stack } from "@mui/material";
import { colors } from "../../constants/colors";

export default function DishItemText({ children, sx }) {
  return (
    <Stack
      direction="row"
      justifyContent="space-between"
      sx={{
        fontSize: 16,
        // fontWeight: 600,
        color: colors.text.sub2,
        ...sx,
      }}
    >
      {children}
    </Stack>
  );
}
