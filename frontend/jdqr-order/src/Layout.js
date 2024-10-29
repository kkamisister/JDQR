/** @jsxImportSource @emotion/react */
import React, { useEffect } from "react";
import { css } from "@emotion/react";
import { colors } from "./constants/colors";

const Layout = ({ children }) => {
  useEffect(() => {
    const updateVh = () => {
      const vh = window.innerHeight * 0.01;
      document.documentElement.style.setProperty("--vh", `${vh}px`);
    };

    updateVh();
    window.addEventListener("resize", updateVh);

    return () => {
      window.removeEventListener("resize", updateVh);
    };
  }, []);

  return (
    <div css={containerStyle}>
      <div css={contentStyle}>{children}</div>
    </div>
  );
};

const containerStyle = css`
  background-color: ${colors.background.primary};
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: calc(var(--vh, 1vh) * 100); /* 뷰포트 높이 반영 */
  overflow: hidden;
`;

const contentStyle = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%; /* 부모 높이를 무조건 채우도록 설정 */
  background-color: #ffffff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);

  @media (min-width: 450px) {
    max-width: 450px; /* 450px 이상일 때 고정 너비 */
  }
`;

export default Layout;
