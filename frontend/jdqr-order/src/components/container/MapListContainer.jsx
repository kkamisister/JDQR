import React from "react"
import { Stack } from "@mui/material"
import { colors } from "../../constants/colors"

const MapListContainer = ({ children, sx, height, spacing }) => {
  return (
    <Stack
      direction="column"
      spacing={spacing}
      sx={{
        position: "relative", // 기존 "sticky"에서 변경
        width: "100%",
        backgroundColor: colors.background.white,
        borderTopLeftRadius: "35px",
        borderTopRightRadius: "35px",
        padding: "10px",
        overflowY: "auto",
        height: height ? `${height}px` : "auto", // 높이 prop 적용
        ...sx,
      }}
    >
      {children}
    </Stack>
  )
}

export default MapListContainer
