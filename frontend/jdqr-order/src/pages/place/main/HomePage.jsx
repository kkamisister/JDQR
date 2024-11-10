import React, { useState } from "react"
import { useQuery } from "@tanstack/react-query"
import { Stack } from "@mui/material"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantListBox from "./RestaurantListBox"
import MapDefaultHeader from "../../../components/header/MapDefaultHeader"
import { fetchRestaurants } from "../../../utils/apis/place"

const HomePage = () => {
  const mockData = {
    status: 200,
    message: "식당 정보 조회에 성공하였습니다",
    data: {
      majorCategories: ["일식", "한식", "중식", "양식", "술집"],
      restaurants: [
        {
          restaurantId: 1,
          restaurantName: "츄라우미",
          restaurantCategories: [
            {
              restaurantCategoryId: 1,
              restaurantCategoryName: "일식",
            },
            {
              restaurantCategoryId: 3,
              restaurantCategoryName: "이자카야",
            },
          ],
          restTableNum: 5, // 남은 테이블 개수
          restSeatNum: 16, // 남은 좌석 개수
          maxPeopleNum: 6, // 최대 몇인 테이블인지
          address: "서울특별시 강남구 역삼2동",
          image:
            "https://cdn.pixabay.com/photo/2020/04/27/09/21/cat-5098930_1280.jpg",
          lat: 37.50127169408985,
          lng: 127.03955376506696,
          open: true,
        },
        {
          restaurantId: 2,
          restaurantName: "양국",
          restaurantCategories: [
            {
              restaurantCategoryId: 1,
              restaurantCategoryName: "중식",
            },
            {
              restaurantCategoryId: 3,
              restaurantCategoryName: "육류",
            },
          ],
          restTableNum: 2, // 남은 테이블 개수
          restSeatNum: 2, // 남은 좌석 개수
          maxPeopleNum: 6, // 최대 몇인 테이블인지
          address: "서울특별시 강남구 역삼1동",
          image:
            "https://cdn.pixabay.com/photo/2020/04/27/09/21/cat-5098930_1280.jpg",
          lat: 37.50185743270306,
          lng: 127.03929961611652,
          open: false,
        },
      ],
    },
  }

  const [bounds, setBounds] = useState(null) // 지도 범위 저장
  const [peopleFilter, setPeopleFilter] = useState(0) // 필터링 옵션 예시

  const {
    data: restaurantsData,
    error,
    isLoading,
  } = useQuery({
    queryKey: ["restaurants", bounds, peopleFilter],
    queryFn: () => fetchRestaurants(bounds, peopleFilter),
    enabled: !!bounds, // bounds가 설정되었을 때만 쿼리 활성화
  })

  console.log("Fetched Restaurants Data:", restaurantsData)

  const handleBoundsChange = (newBounds) => {
    setBounds(newBounds)
  }

  return (
    <Stack>
      <Stack
        sx={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          zIndex: 1,
        }}
      >
        <MapDefaultHeader
          majorCategories={restaurantsData?.majorCategories || []}
        />
        {/* <MapDefaultHeader majorCategories={mockData.data.majorCategories} /> */}
      </Stack>

      <Stack
        sx={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          width: "100%",
          height: "100%",
        }}
      >
        <KakaoMap onBoundsChange={handleBoundsChange} />
      </Stack>

      <Stack
        sx={{
          position: "relative",
          bottom: -700,
          left: 0,
          right: 0,
          maxHeight: "80vh",
          overflowY: "auto",
          zIndex: 1,
        }}
      >
        {/* <RestaurantListBox restaurants={restaurantsData?.restaurants || []} /> */}
        <RestaurantListBox restaurants={mockData.data.restaurants} />
      </Stack>
    </Stack>
  )
}

export default HomePage
