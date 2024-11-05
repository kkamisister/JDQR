import React from "react"
import { Stack } from "@mui/material"
import { colors } from "../../constants/colors"

const MapListBox = ({ children, sx }) => {
  return (
    <Stack
      direction="column"
      spacing={2}
      sx={{
        position: "sticky",
        bottom: 0,
        width: "100%",
        backgroundColor: colors.background.white,
        borderTopLeftRadius: "35px",
        borderTopRightRadius: "35px",
        padding: "10px",
        overflowY: "auto",
        ...sx,
      }}
    >
      {children}
    </Stack>
  )
}

export default MapListBox
