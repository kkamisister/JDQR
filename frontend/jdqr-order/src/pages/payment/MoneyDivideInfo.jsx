import { Stack, Input, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import NumberSelector from "../../components/selector/NumberSelector";
import { useEffect, useState } from "react";

export default function MoneyDivideInfo({
  initTotal = 1,
  totalPrice = 0,
  onValuesChange,
}) {
  const [total, setTotal] = useState(initTotal);
  const [portion, setPortion] = useState(1);
  const [money, setMoney] = useState(0);

  useEffect(() => {
    const validPortion = Math.min(Math.max(portion, 1), total);
    setPortion(validPortion);
    setMoney(Math.ceil((validPortion / total) * totalPrice));
  }, [total, portion]);

  useEffect(() => {
    onValuesChange({ total, portion, money });
  }, [total, portion, money, onValuesChange]);

  return (
    <Stack
      sx={{
        bgcolor: colors.main.primary100,
        borderRadius: 1,
        m: 2,
        p: 2,
      }}
    >
      <Stack direction="row" justifyContent="center" alignItems="center">
        <Typography>총</Typography>
        <NumberSelector value={total} onChange={(value) => setTotal(value)} />
        <Typography>명 중</Typography>
        <NumberSelector
          value={portion}
          onChange={(value) => setPortion(value)}
          maxVal={total}
        />
        <Typography>명 몫을 계산할게요</Typography>
      </Stack>

      <Stack direction="row" justifyContent="center" alignItems="center" mt={2}>
        <Typography>인당 결제 금액:</Typography>
        <Input disabled value={money.toLocaleString()} endAdornment="원" />
      </Stack>
    </Stack>
  );
}
