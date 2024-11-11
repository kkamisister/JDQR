import { Box, Stack } from "@mui/material";
import Header from "../../components/header/Header";
import DishHeader from "./DishHeader";
import DishList from "./DishList";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { fetchDishMenu } from "../../utils/apis/dish";
import LoadingSpinner from "../../components/Spinner/LoadingSpinner";

const DishPage = () => {
  // useQuery({
  //   queryKey: ["initialTableInfo"],
  //   queryFn: async () => {
  //     axiosInstance.extractTableInfo();
  //     await axiosInstance.setUserCoookie();
  //   },
  // });

  const { data, isLoading, isError } = useQuery({
    queryKey: ["dishList"],
    queryFn: fetchDishMenu,
  });

  return (
    <Box
      sx={{
        height: "100vh",
        overflowY: "auto",
        "&::-webkit-scrollbar": {
          display: "none",
        },
        msOverflowStyle: "none",
        scrollbarWidth: "none",
      }}
    >
      <Header title={isLoading ? "" : data.tableName} />
      <DishHeader />
      {isLoading && <LoadingSpinner message={"로딩 중입니다"} />}
      {!isLoading && !isError && (
        <DishList dishes={data.dishes} categories={data.dishCategories} />
      )}
    </Box>
  );
};

export default DishPage;
