import React, { useState } from "react"
import { colors } from "../../../constants/colors"
import { Button, Checkbox, Stack, Typography } from "@mui/material"
import PeopleFilterButton from "../../../components/button/PeopleFilterButton"

const PeopleFilter = () => {
  const [activeIndex, setActiveIndex] = useState(null)
  const [isChecked, setIsChecked] = useState(false)

  const peopleOptions = ["1인", "2인", "4인", "단체"]

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

  const getFilterMessage = () => {
    if (isChecked && activeIndex !== null) {
      return `${peopleOptions[activeIndex]}석이 남아있는 식당을 보고 있어요`
    }
    return ""
  }

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
        {peopleOptions.map((name, index) => (
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
              color: colors.background.box,
              opacity: 0.5,
            },
            "& .MuiSvgIcon-root": {
              fontSize: 25,
            },
          }}
          disabled={isCheckboxDisabled}
          onChange={(e) => setIsChecked(e.target.checked)}
        />
        {isChecked && activeIndex !== null && (
          <Typography fontSize={14} color={colors.main.primary400}>
            {getFilterMessage()}
          </Typography>
        )}
      </Stack>
    </Stack>
  )
}

export default PeopleFilter
