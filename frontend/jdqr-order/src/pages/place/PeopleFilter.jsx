import React, { useState } from "react"
import { colors } from "../../constants/colors"
import { Button, Stack, Typography } from "@mui/material"
import PeopleFilterButton from "../../components/button/PeopleFilterButton"

const PeopleFilter = () => {
  const [activeIndex, setActiveIndex] = useState(null)

  const handleButtonClick = (index) => {
    setActiveIndex(index)
  }
  return (
    <Stack direction="column" spacing={1}>
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
            isActive={activeIndex === index} // 현재 버튼이 활성화 상태인지 확인
            onClick={() => handleButtonClick(index)} // 버튼 클릭 시 핸들러 호출
          />
        ))}
      </Stack>
      <Stack
        direction="row"
        spacing={2}
        alignItems="center"
        paddingLeft="10px"
        whiteSpace="nowrap"
      >
        <Typography fontSize={15}>함게 앉기</Typography>
      </Stack>
    </Stack>
  )
}

export default PeopleFilter
