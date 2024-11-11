import React, { useEffect, useState } from "react"
import { Stack } from "@mui/material"
import { Map, MapMarker, useKakaoLoader } from "react-kakao-maps-sdk"
import activeMapmarker from "../../assets/images/mapmarker1.png"
import inactiveMapmarker from "../../assets/images/mapmarker2.png"

const KakaoMap = ({ onBoundsChange, initialLocation, initialBounds }) => {
  const [isActive, setIsActive] = useState(false)
  const [location, setLocation] = useState({
    lat: 37.50125774784631,
    lng: 127.03956684373539,
  })
  const [bounds, setBounds] = useState(initialBounds || null)

  const isLoaded = useKakaoLoader()

  useEffect(() => {
    if (isLoaded && initialLocation) {
      setLocation(initialLocation)
    }
    if (initialBounds) {
      setBounds(initialBounds)
    }
  }, [isLoaded, initialLocation, initialBounds])

  const handleBoundsChanged = (map) => {
    const bounds = map.getBounds()
    const sw = bounds.getSouthWest()
    const ne = bounds.getNorthEast()

    onBoundsChange({
      minLat: sw.getLat(),
      maxLat: ne.getLat(),
      minLng: sw.getLng(),
      maxLng: ne.getLng(),
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
        bounds={bounds}
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
