import React from "react"
import { colors } from "../../constants/colors"
import { Chip, Stack, Typography } from "@mui/material"

const PeopleFilter = () => {
  return (
    <Stack direction="row" spacing={1} alignItems="center">
      <Typography fontSize={20}>인원 수</Typography>
      <Chip label="1명"></Chip>
      <Chip label="2명"></Chip>
      <Chip label="4명"></Chip>
      <Chip label="단체"></Chip>
    </Stack>
  )
}

export default PeopleFilter
