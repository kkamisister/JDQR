import AppBar from "@mui/material/AppBar";
import { Typography, Toolbar, Chip, IconButton } from "@mui/material";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import { colors } from "../../constants/colors";
import { useNavigate } from "react-router-dom";
import { getTableName } from "../../utils/apis/table";
import { useQuery } from "@tanstack/react-query";

export default function Header({ title, BackPage = false }) {
  const { data: tableName, isLoading } = useQuery({
    queryKey: ["tableName"],
    queryFn: getTableName,
  });
  const navigate = useNavigate();
  const backToPrevPage = () => {
    navigate(-1);
  };

  return (
    <AppBar
      position="sticky"
      elevation={1}
      sx={{
        bgcolor: colors.background.white,
      }}
    >
      <Toolbar>
        {BackPage ? (
          <IconButton onClick={backToPrevPage}>
            <ChevronLeftIcon sx={{ color: colors.text.sub1 }} />
          </IconButton>
        ) : (
          <LocationOnIcon
            sx={{
              color: colors.point.blue,
            }}
          />
        )}
        <Typography
          sx={{
            color: colors.text.main,
            fontWeight: 600,
            flexGrow: 1,
          }}
        >
          {title}
        </Typography>
        {tableName && (
          <Chip
            label={tableName}
            sx={{
              bgcolor: colors.main.primary500,
              borderRadius: "10px",
              color: colors.text.white,
            }}
          />
        )}
      </Toolbar>
    </AppBar>
  );
}
