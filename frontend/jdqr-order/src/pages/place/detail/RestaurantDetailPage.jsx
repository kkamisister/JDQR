import React, { useState } from "react"
import RestaurantDetailBox from "./RestaurantDetailBox"
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader"
import { fetchRestaurantDetail } from "../../../utils/apis/place"
import { useQuery } from "@tanstack/react-query"
import { useParams } from "react-router-dom"
import { Stack } from "@mui/material"

const RestaurantDetailPage = () => {
  const { restaurantId } = useParams()

  const { data: restaurantData, error } = useQuery({
    queryKey: ["restaurant", restaurantId],
    queryFn: async () => {
      // console.log("레스토랑 아이디는 이렇게.....생겼다지......", restaurantId)
      const response = await fetchRestaurantDetail(restaurantId)
      // console.log("api 응답은....이렇게...생겼다지....", response)
      return response
    },
    enabled: !!restaurantId,
  })
  // console.log("당신은.....데이터를...불러왔지...:", restaurantData)

  if (error) {
    console.error("당신은...데이터를 불러오는데 실패했지....:", error)
    return <div>당신은...데이터를 불러오는데 실패했지....</div>
  }

  return (
    <Stack>
      <Stack
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

      <Stack
        style={{
          marginTop: "60px",
          overflowY: "auto",
        }}
      >
        {restaurantData ? (
          <RestaurantDetailBox
            categories={restaurantData.dishInfo.dishCategories}
            dishes={restaurantData.dishInfo.dishes}
            restaurant={restaurantData.restaurant}
          />
        ) : (
          <div>로딩 중...</div>
        )}
      </Stack>
    </Stack>
  )
}

export default RestaurantDetailPage
