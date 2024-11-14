import React from "react"
import { useNavigate } from "react-router-dom"
import { Box, Stack, IconButton } from "@mui/material"
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft"
import { colors } from "../../constants/colors"

const MapBackButtonHeader = () => {
  const navigate = useNavigate()

  const backToPrevPage = () => {
    navigate(-1)
  }

  return (
    <Box sx={{ flexGrow: 1 }}>
      <Stack
        direction="row"
        sx={{
          position: "absolute",
          top: "0",
          left: "0",
          width: "100%",
          backgroundColor: "white",
          padding: "10px 10px",
          boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
          alignItems: "center",
        }}
      >
        <IconButton onClick={backToPrevPage}>
          <ChevronLeftIcon sx={{ color: colors.text.sub1 }} />
        </IconButton>
      </Stack>
    </Box>
  )
}

export default MapBackButtonHeader
