import { Stack, Box, Input } from "@mui/material";
import { colors } from "../../constants/colors";
import NumberSelector from "../../components/selector/NumberSelector";

export default function MoneyDivideInfo() {
  const money = 32423423.23123;
  const bill = Math.ceil(money).toLocaleString();
  return (
    <Stack
      sx={{
        bgcolor: colors.main.primary100,
        borderRadius: 1,
        m: 2,
        p: 2,
      }}
    >
      <Stack direction="row">
        내가 책임질 사람:
        <NumberSelector value={1} />
        / 총인원:
        <NumberSelector value={23} />
      </Stack>
      <Stack direction="row">
        <NumberSelector /> 명 몫을 계산할게요(총인원 : <NumberSelector />명 )
      </Stack>
      <Stack direction="row"></Stack>
      <Stack direction="row">
        인당 결제 금액:
        <Input
          disabled
          value={Math.ceil(money).toLocaleString()}
          endAdornment="원"
        />
      </Stack>
    </Stack>
  );
}
