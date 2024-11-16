import React, { useState } from "react"
import RestaurantDetailBox from "./RestaurantDetailBox"
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader"
import RestaurantInfo from "./RestaurantInfo"
import { fetchRestaurantDetail } from "../../../utils/apis/place"
import { useQuery } from "@tanstack/react-query"
import { useParams } from "react-router-dom"
import { Stack } from "@mui/material"

const RestaurantDetailPage = () => {
  const { restaurantId } = useParams()

  const {
    data: restaurantData,
    error,
    isLoading,
  } = useQuery({
    queryKey: ["restaurant", restaurantId],
    queryFn: async () => {
      console.log("레스토랑 아이디는 이렇게.....생겼다지......", restaurantId)
      const response = await fetchRestaurantDetail(restaurantId)
      console.log("api 응답은....이렇게...생겼다지....", response)
      return response
    },
    enabled: !!restaurantId,
  })
  console.log("당신은.....데이터를...불러왔지...:", restaurantData)

  if (isLoading) {
    return (
      <div>
        정말 여긴....기깔난 맛짐....어떤 메뉴가 있냐하면....기대하시라...
      </div>
    )
  }

  if (error) {
    console.error("당신은...데이터를 불러오는데 실패했지....:", error)
    return <div>당신은...데이터를 불러오는데 실패했지....</div>
  }

  if (!restaurantData || !restaurantData.restaurant) {
    return <div>식당정보...불러오는데 실패했지.....꼭 알아야할까?</div>
  }

  return (
    <Stack>
      <Stack // 1. 백버튼 헤더
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          zIndex: 1,
        }}
      >
        <MapBackButtonHeader />
      </Stack>
      <Stack // 2. 매장 정보
        style={{
          marginTop: "60px",
          overflowY: "auto",
        }}
        padding="10px"
        height="100%"
      >
        <RestaurantInfo restaurant={restaurantData.restaurant} />
      </Stack>

      <Stack // 3. 해당 매장의 메뉴
        style={{
          overflowY: "auto",
        }}
      >
        {restaurantData ? (
          <RestaurantDetailBox
            categories={restaurantData.dishInfo.dishCategories}
            dishes={restaurantData.dishInfo.dishes}
          />
        ) : (
          <div>아 여기 진짜 맛집인데 어떤 메뉴가 있는지 기대하시라</div>
        )}
      </Stack>
    </Stack>
  )
}

export default RestaurantDetailPage
