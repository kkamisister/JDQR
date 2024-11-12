import React, { useState, useEffect } from "react"
import { useQuery } from "@tanstack/react-query"
import { Stack } from "@mui/material"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantListBox from "./RestaurantListBox"
import MapDefaultHeader from "../../../components/header/MapDefaultHeader"
import { fetchRestaurants } from "../../../utils/apis/place"

const HomePage = () => {
  const [bounds, setBounds] = useState(null)
  const [people, setPeople] = useState(0)
  const [together, setTogether] = useState(false)
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
    queryKey: ["restaurants", bounds, people, together],
    queryFn: async () => {
      console.log("파라미터는....이렇게 생겼다지...", {
        bounds,
        people,
        together,
      })
      const response = await fetchRestaurants(bounds, people, together)
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
        <RestaurantListBox
          restaurants={restaurantsData?.restaurants || []}
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
