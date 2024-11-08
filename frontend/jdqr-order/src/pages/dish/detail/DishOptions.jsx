import { Stack, Typography, FormGroup, FormControlLabel } from "@mui/material";
import { colors } from "../../../constants/colors";
import { CheckBox } from "@mui/icons-material";

export default function DishOptions({ options }) {
  return (
    <Stack>
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
        <Stack key={option.optionId}>
          <Typography sx={{ fontSize: 16, fontWeight: 600 }}>
            {option.optionName}
          </Typography>
          <FormGroup>
            {option.choices.map((choice) => (
              <FormControlLabel
                key={choice.choiceId}
                control={
                  <CheckBox
                    sx={{
                      color: colors.main.primary500,
                    }}
                  />
                }
                label={choice.choiceName}
              />
            ))}
          </FormGroup>
        </Stack>
      ))}
    </Stack>
  );
}
