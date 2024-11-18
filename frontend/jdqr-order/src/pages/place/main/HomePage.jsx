import React, { useState, useEffect } from "react"
import { useQuery } from "@tanstack/react-query"
import { Stack } from "@mui/material"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantListBox from "./RestaurantListBox"
import MapDefaultHeader from "../../../components/header/MapDefaultHeader"
import {
  fetchRestaurants,
  fetchRestaurantSearch,
} from "../../../utils/apis/place"

const HomePage = () => {
  const [bounds, setBounds] = useState(null)
  const [people, setPeople] = useState(0)
  const [together, setTogether] = useState(false)
  const [location, setLocation] = useState({
    lat: 37.50125774784631,
    lng: 127.03956684373539,
  })
  const [keyword, setKeyword] = useState("")
  const [selectedCategory, setCategory] = useState("")
  const [selectedMapmarker, setSelectedMapmarker] = useState(null)

  useEffect(() => {
    // 사용자 현위치
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
    queryKey: [
      "restaurants",
      bounds,
      people,
      together,
      keyword,
      selectedCategory,
    ],
    queryFn: async () => {
      // keyword 여부에 따라 다른 api 호출
      if (keyword) {
        // console.log("파라미터는....이렇게 생겼다지...", {
        //   bounds,
        //   people,
        //   together,
        //   keyword,
        // })
        const response = await fetchRestaurantSearch({
          ...bounds,
          people,
          together,
          keyword,
        })
        // console.log("api 응답은....이렇게 생겼다지....:", response)
        return response
      } else {
        const response = await fetchRestaurants({
          ...bounds,
          people,
          together,
        })
        return response
      }
    },
    enabled: !!bounds,
  })
  // console.log("당신은...데이터를...불러왔지..:", restaurantsData)

  const handleBoundsChange = (newBounds) => {
    setBounds(newBounds)
  }
  const filteredRestaurants = selectedCategory
    ? restaurantsData?.restaurants?.filter(
        (restaurant) =>
          restaurant.restaurantCategories?.major[0]?.restaurantCategoryName ===
          selectedCategory
      )
    : restaurantsData?.restaurants || []

  const visibleRestaurants = selectedMapmarker
    ? (filteredRestaurants || []).filter(
        (restaurant) => restaurant.id === selectedMapmarker
      )
    : filteredRestaurants || []

  // console.log("selectedMapmarker:", selectedMapmarker)
  // console.log("selectedCategory: ", selectedCategory)
  // console.log("restaurantsData: ", [restaurantsData])
  // console.log("KakaoMap에 전달되는 restaurants 리스트:", filteredRestaurants)
  // console.log(
  //   "RestaurantListBox에 전달되는 restaurants 리스트:",
  //   visibleRestaurants
  // )

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
          setKeyword={setKeyword}
          setCategory={setCategory}
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
          restaurants={filteredRestaurants || []}
          selectedMapmarker={selectedMapmarker}
          setSelectedMapmarker={setSelectedMapmarker}
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
        <RestaurantListBox
          restaurants={visibleRestaurants}
          people={people}
          setPeople={setPeople}
          together={together}
          setTogether={setTogether}
        />
      </Stack>
    </Stack>
  )
}

export default HomePage
