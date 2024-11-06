import { Chip } from "@mui/material";
import { colors } from "../../constants/colors";

const colorOptions = colors.table;

const tagColorMap = new Map();

const getColor = (label) => {
  if (!tagColorMap.has(label)) {
    const color = colorOptions[tagColorMap.size % colorOptions.length];
    tagColorMap.set(label, color);
  }
  return tagColorMap.get(label);
};

const DishTagChip = ({ label, ...props }) => {
  return (
    <Chip
      label={label}
      sx={{
        height: "20px",
        bgcolor: getColor(label),
        fontSize: "12px",
        fontWeight: 600,
        color: colors.text.white,
        mr: 0.5,
        "& .MuiChip-label": {
          paddingX: "8px",
          paddingY: "4px",
        },
      }}
      {...props}
    />
  );
};

export default DishTagChip;
