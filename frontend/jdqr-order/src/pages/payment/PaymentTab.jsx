import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import { colors } from "../../constants/colors";

export default function PaymentTab({
  orderList,
  onTabClick,
  activeTab,
  paymentType,
}) {
  return (
    <Box>
      <Tabs
        value={activeTab}
        onChange={(e, newVal) => onTabClick(newVal)}
        textColor="inherit"
        TabIndicatorProps={{
          style: {
            backgroundColor: colors.main.primary500,
          },
        }}
      >
        {orderList.map((tab, index) => {
          const isDisabled =
            (paymentType === "MONEY_DIVIDE" && index !== 0) ||
            (paymentType === "MENU_DIVIDE" && index !== 1);

          return (
            <Tab
              key={index}
              label={tab}
              disabled={!!paymentType && isDisabled} // paymentType이 있을 때만 비활성화 처리
              sx={{
                fontWeight: "bold",
                fontSize: "16px",
                minWidth: "50%",
                color: isDisabled ? colors.text.sub3 : "inherit", // 비활성화 시 색상 변경
              }}
            />
          );
        })}
      </Tabs>
    </Box>
  );
}
