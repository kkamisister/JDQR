import AppBar from "@mui/material/AppBar";
import { Box, Typography, Toolbar, Chip } from "@mui/material";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import { colors } from "../../constants/colors"; // named export인지 확인

export default function Header({ tableName }) {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar
        position="static"
        elevation={1}
        sx={{
          bgcolor: colors.background.white,
        }}
      >
        <Toolbar>
          <LocationOnIcon
            sx={{
              color: colors.point.blue,
            }}
          />
          <Typography
            sx={{
              color: colors.text.main,
              fontWeight: 600,
              flexGrow: 1,
            }}
          >
            {tableName}
          </Typography>
          <Chip
            label="3번 테이블"
            sx={{
              bgcolor: colors.text.sub2,
              borderRadius: "10px",
              color: colors.text.white,
            }}
          />
        </Toolbar>
      </AppBar>
    </Box>
  );
}
