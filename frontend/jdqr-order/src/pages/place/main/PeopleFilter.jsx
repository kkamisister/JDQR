import React, { useState } from "react"
import { colors } from "../../../constants/colors"
import { Checkbox, Stack, Typography } from "@mui/material"
import PeopleFilterButton from "../../../components/button/PeopleFilterButton"

const PeopleFilter = ({ people, setPeople, together, setTogether }) => {
  const [activeIndex, setActiveIndex] = useState(null)

  const peopleOptions = ["1인", "2인", "4인", "단체"]

  const handleButtonClick = (index) => {
    if (activeIndex === index) {
      setActiveIndex(null)
      setPeople(0)
    } else {
      const peopleValues = [1, 2, 4, 5]
      setPeople(peopleValues[index])
      setActiveIndex(index)
    }
  }

  const handleCheckboxChange = (e) => {
    setTogether(e.target.checked)
  }
  const getFilterMessage = () => {
    if (together && activeIndex !== null) {
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
            activeIndex === null
              ? colors.background.box
              : colors.main.primary400
          }
        >
          함께 앉기
        </Typography>
        <Checkbox
          checked={together}
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
          disabled={activeIndex === null} // 인원 수가 선택되지 않으면 비활성화
          onChange={handleCheckboxChange}
        />
        {together && activeIndex !== null && (
          <Typography fontSize={14} color={colors.main.primary400}>
            {getFilterMessage()}
          </Typography>
        )}
      </Stack>
    </Stack>
  )
}

export default PeopleFilter
