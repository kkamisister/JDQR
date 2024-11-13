import React, { useEffect, useState } from "react"
import { Stack, Typography, Box } from "@mui/material"
import {
  Map,
  MapMarker,
  useKakaoLoader,
  MarkerClusterer,
  CustomOverlayMap,
} from "react-kakao-maps-sdk"
import currLocatMapmarker from "../../assets/images/currLocatMapmarker.png"
import { colors } from "../../constants/colors"

const KakaoMap = ({
  onBoundsChange,
  initialLocation,
  initialBounds,
  restaurants,
}) => {
  const [isActive, setIsActive] = useState(false)
  const [location, setLocation] = useState({
    lat: 37.50125774784631,
    lng: 127.03956684373539,
  })
  const [bounds, setBounds] = useState(initialBounds || null)
  const [selectedMapmarker, setSelectedMapmarker] = useState(null)

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
          // backgroundColor: "rgba(255, 255, 255, 0.7)", // 맵 배경을 살짝 옅게
          // filter: "brightness(0.9)",
        }}
        level={3}
        draggable
        scrollwheel
        onDragEnd={(map) => handleBoundsChanged(map)}
        onZoomChanged={(map) => handleBoundsChanged(map)}
        bounds={bounds}
      >
        {/* 현재 위치 마커 */}
        <MapMarker
          position={location}
          image={{
            src: currLocatMapmarker,
            size: { width: 35, height: 35 },
          }}
        />
        <MarkerClusterer averageCenter={true} minLevel={3}>
          {/* 식당 마커 추가 */}
          {restaurants?.map((restaurant) => (
            <CustomOverlayMap
              key={restaurant.id}
              position={{
                lat: restaurant.lat,
                lng: restaurant.lng,
              }}
              yAnchor={1}
            >
              <Stack alignItems="center" spacing={1}>
                <Stack
                  sx={{
                    padding: "5px",
                    fontSize: "13px",
                    backgroundColor:
                      restaurant.open && restaurant.restTableNum > 0
                        ? colors.main.primary500
                        : colors.text.sub2,
                    color: colors.text.white,
                    borderRadius: "8px",
                    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
                    width: "70px",
                    height: "40px",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                  }}
                >
                  {restaurant.open ? (
                    restaurant.restTableNum > 0 ? (
                      <Stack>
                        {restaurant.restSeatNum}석 / {restaurant.restTableNum}T
                      </Stack>
                    ) : (
                      <Stack>{restaurant.restTableNum} Table</Stack>
                    )
                  ) : (
                    <Typography>영업 종료</Typography>
                  )}
                </Stack>
                <Typography fontSize={16} fontWeight={600}>
                  {restaurant.restaurantName}
                </Typography>
              </Stack>
            </CustomOverlayMap>
          ))}
        </MarkerClusterer>
      </Map>
    </Stack>
  )
}

export default KakaoMap
