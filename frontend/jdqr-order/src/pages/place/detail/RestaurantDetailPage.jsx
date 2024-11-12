import React, { useState } from "react"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantDetailBox from "./RestaurantDetailBox"
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader"
import { fetchRestaurantDetail } from "../../../utils/apis/place"
import { useQuery } from "@tanstack/react-query"
import { useParams } from "react-router-dom"

const RestaurantDetailPage = () => {
  // const [restaurantId, setRestaurantId] = useState(null)
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
    <>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          zIndex: 1,
        }}
      >
        <MapBackButtonHeader />
      </div>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          width: "100%",
          height: "100%",
        }}
      >
        <KakaoMap />
      </div>
      <div
        style={{
          position: "relative",
          bottom: -700,
          left: 0,
          right: 0,
          maxHeight: "80vh",
          overflowY: "auto",
          zIndex: 1,
        }}
      >
        {restaurantData ? (
          <RestaurantDetailBox
            categories={restaurantData.dishInfo.dishCategories}
            dishes={restaurantData.dishInfo.dishes}
            restaurant={restaurantData.restaurant} // restaurant 정보를 추가로 전달
          />
        ) : (
          <div>로딩 중...</div>
        )}
      </div>
    </>
  )
}

export default RestaurantDetailPage
