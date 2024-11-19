import React from "react"
import { Stack } from "@mui/material"
import { colors } from "../../constants/colors"

const MapListContainer = ({ spacing, sx, children }) => {
  return (
    <Stack
      spacing={spacing}
      sx={{
        width: "100%",
        backgroundColor: colors.background.white,
        borderTopLeftRadius: "35px",
        borderTopRightRadius: "35px",
        padding: "10px",
        ...sx,
      }}
    >
      {children}
    </Stack>
  )
}

export default MapListContainer
