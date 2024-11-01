import React, { useState } from "react"
import { Stack, Button } from "@mui/material"
import { Map, MapMarker, useKakaoLoader } from "react-kakao-maps-sdk"
import activeMapmarker from "../../assets/images/mapmarker1.png"
import inactiveMapmarker from "../../assets/images/mapmarker2.png"
import zIndex from "@mui/material/styles/zIndex"

const { kakao } = window

const KakaoMap = () => {
  const [isActive, setIsActive] = useState(false)

  useKakaoLoader()

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function (position) {
      const lat = position.coords.latitude
      const lon = position.coords.longitude

      const locPosition = new kakao.maps.LatLng(lat, lon)

      displayMarker(locPosition)
    })
  } else {
    const locPosition = new kakao.maps.LatLng(
      37.50125774784631,
      127.03956684373539
    )

    displayMarker(locPosition)
  }

  function displayMarker(locPosition) {
    const marker = new kakao.maps.Marker({ locPosition })
  }

  // const customOverlay = new kakao.mapsCustomOverlay({
  //   position: position,
  //   content: content,
  // })

  // function initializeMap() {
  //   return new Promise((resolve) => {
  //     const container = document.getElementById("map")
  //     const options = {
  //       center: new kakao.maps.Latlng(37.50125774784631, 127.03956684373539),
  //       level: 10,
  //     }
  //     const map = new kakao.maps.Map(container, options)
  //     resolve(map)
  //   })
  // }

  return (
    <Stack>
      <Map
        id="map"
        center={{
          lat: 37.50125774784631,
          lng: 127.03956684373539,
        }}
        style={{
          width: "100%",
          height: "45vh",
        }}
        level={3}
        draggable={true}
        scrollwheel={true}
      >
        <MapMarker
          position={{ lat: 37.50125774784631, lng: 127.03956684373539 }}
          image={{
            src: isActive ? activeMapmarker : inactiveMapmarker,
            size: {
              width: 80,
              height: 60,
            },
          }}
          onClick={() => setIsActive(!isActive)} // 일단 클릭 시 활성상태 전환으로 설정해둠~~
        ></MapMarker>
      </Map>
    </Stack>
  )
}

export default KakaoMap
