import { useEffect, useState } from "react";
import { Typography, Box } from "@mui/material";
import { keyframes } from "@emotion/react";
import { useNavigate } from "react-router-dom";

// 흔들리는 애니메이션 정의
const shakeAnimation = keyframes`
  0% { transform: translate(0, 0); }
  25% { transform: translate(-10px, 0); }
  50% { transform: translate(10px, 0); }
  75% { transform: translate(-10px, 0); }
  100% { transform: translate(0, 0); }
`;

const CelebrationPage = () => {
  const navigate = useNavigate();
  const [emoji, setEmoji] = useState("🎉");

  useEffect(() => {
    const emojiInterval = setInterval(() => {
      setEmoji((prev) => (prev === "🎉" ? "👏" : "🎉"));
    }, 500); // 0.5초마다 이모티콘 전환

    const redirectTimeout = setTimeout(() => {
      navigate("/dish");
    }, 3000); // 3초 후 리다이렉트

    return () => {
      clearInterval(emojiInterval);
      clearTimeout(redirectTimeout);
    };
  }, [navigate]);

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "80vh",
      }}
    >
      <Typography
        variant="h1"
        sx={{
          fontSize: "5rem",
          animation: `${shakeAnimation} 0.5s infinite`,
        }}
      >
        {emoji}
      </Typography>
      <Typography>모든 결제가 완료되었습니다!</Typography>
    </Box>
  );
};

export default CelebrationPage;
