import SearchIcon from "@mui/icons-material/Search";
import { TextField, Box } from "@mui/material";
import { colors } from "../../constants/colors";

const DishSearchBar = ({}) => {
  function onKeywordChange() {
    return;
  }
  return (
    <Box
      sx={{
        maxWidth: "100%",
        display: "flex",
        border: "solid",
        borderColor: colors.background.box,
        borderRadius: "100px",
        m: 2,
        px: 3,
      }}
    >
      <TextField
        placeholder="메뉴명을 검색하세요"
        onChange={(event) => {
          onKeywordChange(event.target.value);
        }}
        fullWidth
        sx={{
          "& .MuiOutlinedInput-root": {
            height: "40px", // 높이 변경
            "& fieldset": {
              border: "none", // 기본 외곽선 없앰
            },
            "&:hover fieldset": {
              border: "none", // hover 시 외곽선 없앰
            },
          },
        }}
      />

      <SearchIcon
        sx={{
          alignSelf: "center",
        }}
      />
    </Box>
  );
};

export default DishSearchBar;
