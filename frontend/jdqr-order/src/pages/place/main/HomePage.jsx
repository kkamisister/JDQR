import React, { useState, useEffect } from "react"
import { useQuery } from "@tanstack/react-query"
import { Stack } from "@mui/material"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantListBox from "./RestaurantListBox"
import MapDefaultHeader from "../../../components/header/MapDefaultHeader"
// import { fetchRestaurants } from "../../../utils/apis/place";

// Mock data for Gangnam Station area
const mockData = {
  majorCategories: ["Korean", "Cafe", "Fast Food", "Japanese", "BBQ"],
  restaurants: [
    {
      id: 1,
      restaurantName: "츄라우미",
      restaurantCategories: {
        major: {
          restaurantCategoryId: 9,
          restaurantCategoryName: "BBQ",
        },
        minor: [
          {
            restaurantCategoryId: 2,
            restaurantCategoryName: "Korean",
          },
        ],
      },
      restTableNum: 5,
      restSeatNum: 20,
      maxPeopleNum: 10,
      address: "Gangnam-daero 123, Seoul, South Korea",
      image: "gangnam_bbq.jpg",
      lat: 37.4981,
      lng: 127.0276,
      open: true,
    },
    {
      id: 2,
      restaurantName: "초오밥",
      restaurantCategories: {
        major: {
          restaurantCategoryId: 3,
          restaurantCategoryName: "Japanese",
        },
        minor: [
          {
            restaurantCategoryId: 2,
            restaurantCategoryName: "Japanese",
          },
          {
            restaurantCategoryId: 2,
            restaurantCategoryName: "fish",
          },
        ],
      },
      restTableNum: 3,
      restSeatNum: 12,
      maxPeopleNum: 6,
      address: "Gangnam-daero 456, Seoul, South Korea",
      image: "seoul_sushi.jpg",
      lat: 37.5001,
      lng: 127.0278,
      open: true,
    },
    {
      id: 3,
      restaurantName: "바나프레소",
      restaurantCategories: {
        major: {
          restaurantCategoryId: 5,
          restaurantCategoryName: "Cafe",
        },
        minor: [
          {
            restaurantCategoryId: 2,
            restaurantCategoryName: "Korean",
          },
        ],
      },
      restTableNum: 4,
      restSeatNum: 15,
      maxPeopleNum: 8,
      address: "Gangnam-daero 789, Seoul, South Korea",
      image: "gangnam_cafe.jpg",
      lat: 37.4985,
      lng: 127.0295,
      open: true,
    },
    {
      id: 4,
      restaurantName: "바스버거",
      restaurantCategories: {
        major: {
          restaurantCategoryId: 1,
          restaurantCategoryName: "Fast Food",
        },
        minor: [
          {
            restaurantCategoryId: 2,
            restaurantCategoryName: "Korean",
          },
        ],
      },
      restTableNum: 6,
      restSeatNum: 25,
      maxPeopleNum: 12,
      address: "Gangnam-daero 1001, Seoul, South Korea",
      image: "burger_place.jpg",
      lat: 37.4976,
      lng: 127.0266,
      open: true,
    },
  ],
}

const HomePage = () => {
  const [bounds, setBounds] = useState(null)
  const [peopleFilter, setPeopleFilter] = useState(0)
  const [location, setLocation] = useState({
    lat: 37.50125774784631,
    lng: 127.03956684373539,
  })

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const userLocation = {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          }
          setLocation(userLocation)

          const latOffset = 0.01
          const lngOffset = 0.01
          setBounds({
            minLat: userLocation.lat - latOffset,
            maxLat: userLocation.lat + latOffset,
            minLng: userLocation.lng - lngOffset,
            maxLng: userLocation.lng + lngOffset,
          })
        },
        () => {
          console.warn("당신은....지도 불러오기를...실패했지....")
        }
      )
    }
  }, [])

  const { data: restaurantsData } = useQuery({
    queryKey: ["restaurants", bounds, peopleFilter],
    queryFn: async () => {
      console.log("파라미터는....이렇게 생겼다지...", { bounds, peopleFilter })
      // const response = await fetchRestaurants(bounds, peopleFilter);
      const response = mockData // Use mockData instead of API call
      console.log("api 응답은....이렇게 생겼다지....:", response)
      return response
    },
    enabled: !!bounds, // bounds가 설정되었을 때만 쿼리 활성화
  })
  console.log("당신은...데이터를...불러왔지..:", restaurantsData)

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
        <KakaoMap
          onBoundsChange={handleBoundsChange}
          initialLocation={location} // 초기 위치 전달
          initialBounds={bounds} // 초기 bounds 전달
          restaurants={restaurantsData?.restaurants || []}
        />
      </Stack>

      <Stack
        sx={{
          position: "relative",
          left: 0,
          right: 0,
          maxHeight: "80vh",
          overflowY: "auto",
          zIndex: 1,
          marginTop: "680px",
        }}
      >
        <RestaurantListBox restaurants={restaurantsData?.restaurants || []} />
      </Stack>
    </Stack>
  )
}

export default HomePage
