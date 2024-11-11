import React, { useEffect, useState } from "react"
import { Stack } from "@mui/material"
import { Map, MapMarker, useKakaoLoader } from "react-kakao-maps-sdk"
import activeMapmarker from "../../assets/images/mapmarker1.png"
import inactiveMapmarker from "../../assets/images/mapmarker2.png"

const KakaoMap = ({ onBoundsChange }) => {
  const [isActive, setIsActive] = useState(false)
  const [location, setLocation] = useState({
    lat: 37.50125774784631,
    lng: 127.03956684373539,
  })

  const isLoaded = useKakaoLoader()

  useEffect(() => {
    // 사용자의 현재 위치 가져오기
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

  const handleBoundsChanged = (map) => {
    const bounds = map.getBounds()
    const sw = bounds.getSouthWest()
    const ne = bounds.getNorthEast()

    onBoundsChange({
      swLat: sw.getLat(),
      neLat: ne.getLat(),
      swLng: sw.getLng(),
      neLng: ne.getLng(),
    })
  }

  return (
    <Stack sx={{ width: "100%", height: "100%" }}>
      <Map
        id="map"
        center={location}
        style={{
          top: 0,
          width: "100%",
          height: "100%",
        }}
        level={3}
        draggable
        scrollwheel
        onDragEnd={(map) => handleBoundsChanged(map)}
        onZoomChanged={(map) => handleBoundsChanged(map)}
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
