import { Stack, Input, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import NumberSelector from "../../components/selector/NumberSelector";
import { useState, useEffect } from "react";

export default function MoneyDivideInfo({
  initTotal,
  initPortion,
  totalPrice,
}) {
  const [total, setTotal] = useState(initTotal || 1);
  const [portion, setPortion] = useState(initPortion || 1);
  const [money, setMoney] = useState(0);

  useEffect(() => {
    if (total > 0 && portion > 0) {
      setMoney(totalPrice * (portion / total));
      // console.log(totalPrice, "이게 토탈");
      // console.log(money);
    } else {
      setMoney(0);
    }
  }, [total, portion, totalPrice]);

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
        />
        <Typography>{`(총액: ${totalPrice.toLocaleString()}원)`}</Typography>
      </Stack>
    </Stack>
  );
}
