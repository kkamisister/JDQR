import { Stack, Typography, Checkbox, Box } from "@mui/material";
import { colors } from "../../../constants/colors";

export default function DishOptions({
  options = [],
  selectedOptions,
  handleOptionChange,
}) {
  // console.log("이것이 옵션", options);
  return (
    <Stack spacing={2}>
      <Typography
        sx={{
          fontSize: 20,
          fontWeight: 600,
          color: colors.text.main,
        }}
      >
        옵션
      </Typography>

      {options.map((option) => (
        <Stack key={option.optionId} spacing={1}>
          <Typography
            sx={{ fontSize: 16, fontWeight: 600, color: colors.text.main }}
          >
            {option.optionName}
          </Typography>

          {/* 오타 수정: chocies -> choices */}
          {option.choices?.map((choice) => (
            <Stack
              // pr={2}
              key={choice.choiceId}
              direction="row"
              alignItems="center"
              onClick={() =>
                handleOptionChange(option.optionId, choice.choiceId)
              }
            >
              <Checkbox
                checked={selectedOptions[option.optionId] === choice.choiceId}
                sx={{
                  color: colors.text.sub2,
                  "&.Mui-checked": {
                    color: colors.main.primary500, // 선택된 상태에서 색상 설정
                  },
                }}
              />
              {/* 옵션 이름 */}
              <Typography fontSize={18}>{choice.choiceName}</Typography>
              {/* 점선 */}
              <Box
                component="span"
                sx={{
                  borderBottom: `1px dotted ${colors.text.sub2}`,
                  flexGrow: 1,
                  mx: 1,
                }}
              />
              {/* 가격 */}
              <Typography>{choice.price.toLocaleString()}원</Typography>
            </Stack>
          ))}
        </Stack>
      ))}
    </Stack>
  );
}
