import { Stack, Input, Typography } from "@mui/material";
import { colors } from "../../constants/colors";
import NumberSelector from "../../components/selector/NumberSelector";
import { useState, useEffect } from "react";
import { usePaymentStore } from "../../stores/paymentStore";

export default function MoneyDivideInfo({
  initTotal,
  initPortion,
  totalPrice,
}) {
  const [total, setTotal] = useState(initTotal || 1);
  const [portion, setPortion] = useState(initPortion || 1);
  const setMoney = usePaymentStore((state) => state.setMoney);

  useEffect(() => {
    if (total > 0 && portion > 0) {
      setMoney(totalPrice * (portion / total));
    } else {
      setMoney(0);
    }
  }, [total, portion, totalPrice, setMoney]);

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
          value={(total > 0 && portion > 0
            ? Math.ceil(totalPrice * (portion / total))
            : 0
          ).toLocaleString()}
          endAdornment="원"
        />
        {/* <Typography>{`(총액: ${totalPrice.toLocaleString()}원)`}</Typography> */}
      </Stack>
    </Stack>
  );
}
