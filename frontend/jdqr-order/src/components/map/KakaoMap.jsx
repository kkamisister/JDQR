import React, { useEffect, useState } from "react"
import { Stack } from "@mui/material"
import { Map, MapMarker, useKakaoLoader } from "react-kakao-maps-sdk"
import activeMapmarker from "../../assets/images/mapmarker1.png"
import inactiveMapmarker from "../../assets/images/mapmarker2.png"

const KakaoMap = () => {
  const [isActive, setIsActive] = useState(false)
  const [location, setLocation] = useState({
    lat: 37.50125774784631,
    lng: 127.03956684373539,
  })

  const isLoaded = useKakaoLoader()

  useEffect(() => {
    if (isLoaded && navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setLocation({
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          })
        },
        () => {
          console.warn("Geolocation not available or permission denied.")
        }
      )
    }
  }, [isLoaded])

  return (
    <Stack>
      <Map
        id="map"
        center={location}
        style={{
          top: 0,
          width: "100%",
          height: "60vh",
        }}
        level={3}
        draggable
        scrollwheel
      >
        <MapMarker
          position={location}
          image={{
            src: isActive ? activeMapmarker : inactiveMapmarker,
            size: { width: 80, height: 60 },
          }}
          onClick={() => setIsActive(!isActive)}
        />
      </Map>
    </Stack>
  )
}

export default KakaoMap
