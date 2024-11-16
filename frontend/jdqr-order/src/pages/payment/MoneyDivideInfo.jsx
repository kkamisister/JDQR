import { Stack, Box, Input, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import NumberSelector from "../../components/selector/NumberSelector";
import { useState, useEffect } from "react";

export default function MoneyDivideInfo({ initTotal, initPortion }) {
  const [total, setTotal] = useState(initTotal);
  const [portion, setPortion] = useState(initPortion);

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
      <Stack direction="row" justifyContent="center">
        총 <NumberSelector value={total} onChange={setTotal} /> 명 중
        <NumberSelector value={portion} onChange={setPortion} maxVal={total} />
        명 몫을 계산할게요
      </Stack>

      <Stack direction="row" justifyContent="center" alignItems="center">
        <Typography>인당 결제 금액:</Typography>
        <Input
          disabled
          value={Math.ceil(money).toLocaleString()}
          endAdornment="원"
          sx={{}}
        />
      </Stack>
    </Stack>
  );
}
