import React, { useState } from "react"
import { colors } from "../../constants/colors"
import { Button, Checkbox, Stack, Typography } from "@mui/material"
import PeopleFilterButton from "../../components/button/PeopleFilterButton"

const PeopleFilter = () => {
  const [activeIndex, setActiveIndex] = useState(null)
  const [isChecked, setIsChecked] = useState(false)

  const handleButtonClick = (index) => {
    setActiveIndex((prevIndex) => {
      const newIndex = prevIndex === index ? null : index

      if (newIndex === null) {
        setIsChecked(false)
      } else {
        setIsChecked(true)
      }
      console.log(isChecked)
      return newIndex
    })
  }

  const isCheckboxDisabled = activeIndex === null

  return (
    <Stack direction="column" spacing={1} height="70px">
      <Stack
        direction="row"
        spacing={2}
        alignItems="center"
        paddingLeft="10px"
        whiteSpace="nowrap"
      >
        <Typography fontSize={15}>인원 수</Typography>
        {["1인", "2인", "4인", "단체"].map((name, index) => (
          <PeopleFilterButton
            key={index}
            name={name}
            isActive={activeIndex === index}
            onClick={() => handleButtonClick(index)}
          />
        ))}
      </Stack>
      <Stack
        direction="row"
        // spacing={1}
        alignItems="center"
        paddingLeft="10px"
        whiteSpace="nowrap"
      >
        <Typography
          fontSize={15}
          color={
            isCheckboxDisabled ? colors.background.box : colors.main.primary400
          }
        >
          함께 앉기
        </Typography>
        <Checkbox
          checked={isChecked}
          sx={{
            color: colors.background.box,
            "&.Mui-checked": {
              color: colors.main.primary400,
            },
            "&.Mui-disabled": {
              color: colors.background.box, // 비활성화 상태의 색상
              opacity: 0.5, // 비활성화 상태의 불투명도
            },
            "& .MuiSvgIcon-root": {
              fontSize: 25,
            },
          }}
          disabled={isCheckboxDisabled}
          onChange={(e) => setIsChecked(e.target.checked)}
        />
      </Stack>
    </Stack>
  )
}

export default PeopleFilter
