import React from "react"
import { useNavigate } from "react-router-dom"
import { Box, Toolbar, IconButton } from "@mui/material"
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft"
import { colors } from "../../constants/colors"

const MapBackButtonHeader = () => {
  const navigate = useNavigate()

  const backToPrevPage = () => {
    navigate(-1)
  }
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Toolbar
        sx={{
          position: "absolute",
          top: "20px",
          display: "flex",
          justifyContent: "flex-start",
          boxShadow: "none",
        }}
      >
        <IconButton
          onClick={backToPrevPage}
          sx={{
            bgcolor: "white",
            borderRadius: "50%",
            padding: "10px",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
          }}
        >
          <ChevronLeftIcon sx={{ color: colors.text.sub1 }} />
        </IconButton>
      </Toolbar>
    </Box>
  )
}

export default MapBackButtonHeader
