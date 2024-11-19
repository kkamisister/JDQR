import React, { useState } from "react"
import { IconButton, Collapse, Box, Typography } from "@mui/material"
import ExpandMoreIcon from "@mui/icons-material/ExpandMore"
import ExpandLessIcon from "@mui/icons-material/ExpandLess"
import { colors } from "../../constants/colors"
import DishItemCard from "./DishItemCard"

const RestaurantDetailDishItemCard = ({ dish }) => {
  const [expandedOptions, setExpandedOptions] = useState({})

  // 특정 옵션 열고닫아버려
  const toggleOption = (optionId) => {
    setExpandedOptions((prev) => ({
      ...prev,
      [optionId]: !prev[optionId],
    }))
  }

  return (
    <DishItemCard dish={dish}>
      {dish.options && dish.options.length > 0 && (
        <Box sx={{ marginTop: "8px" }}>
          {dish.options.map((option) => (
            <Box key={option.optionId} sx={{ marginBottom: "8px" }}>
              {/* 옵션 헤더 */}
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                  cursor: "pointer",
                  padding: "8px",
                  backgroundColor: colors.background.primary,
                  borderRadius: "4px",
                }}
                onClick={() => toggleOption(option.optionId)}
              >
                {/* 옵션면 */}
                <Typography variant="body1" color={colors.text.main}>
                  {option.optionName}
                </Typography>
                <IconButton
                  sx={{
                    color: colors.text.sub2,
                    padding: 0,
                  }}
                >
                  {expandedOptions[option.optionId] ? (
                    <ExpandLessIcon />
                  ) : (
                    <ExpandMoreIcon />
                  )}
                </IconButton>
              </Box>

              {/* 옵션 상세 */}
              <Collapse in={expandedOptions[option.optionId]} timeout="auto">
                <Box
                  sx={{
                    marginTop: "4px",
                    padding: "8px",
                    backgroundColor: colors.background.white,
                    borderRadius: "4px",
                  }}
                >
                  {option.choices.map((choice) => (
                    <Box
                      key={choice.choiceId}
                      sx={{
                        display: "flex",
                        justifyContent: "space-between",
                        padding: "4px 0",
                      }}
                    >
                      {/* 상세옵션 이름 */}
                      <Typography variant="body2" color={colors.text.main}>
                        {choice.choiceName}
                      </Typography>
                      {/* 상세옵션 가격 */}
                      <Typography variant="body2" color={colors.text.sub}>
                        {choice.price > 0
                          ? `${choice.price.toLocaleString()}원`
                          : "무료"}
                      </Typography>
                    </Box>
                  ))}
                </Box>
              </Collapse>
            </Box>
          ))}
        </Box>
      )}
    </DishItemCard>
  )
}

export default RestaurantDetailDishItemCard
