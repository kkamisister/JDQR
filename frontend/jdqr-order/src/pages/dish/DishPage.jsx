import { Box, Stack } from "@mui/material";
import Header from "../../components/header/Header";
import DishHeader from "./DishHeader";
import DishList from "./DishList";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import axiosInstance from "../../utils/apis/axiosInstance";
import { fetchDishMenu } from "../../utils/apis/dish";

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
      <Header title={data.tableName} />
      <DishHeader />
      <DishList dishes={data.dishes} />
    </Box>
  );
};

export default DishPage;
