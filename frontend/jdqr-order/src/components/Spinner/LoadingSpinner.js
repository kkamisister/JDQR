import { Box, CircularProgress, Typography } from "@mui/material";
import { colors } from "../../constants/colors";

const LoadingSpinner = ({ message }) => {
  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      height="100vh"
    >
      <CircularProgress
        size={120}
        sx={{
          color: colors.main.primary500,
        }}
      />
      {message && (
        <Typography mt={2} variant="body1" fontSize={24} fontWeight={600}>
          {message}
        </Typography>
      )}
    </Box>
  );
};

export default LoadingSpinner;
