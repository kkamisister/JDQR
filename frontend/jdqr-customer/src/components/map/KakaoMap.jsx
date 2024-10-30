import React, { useRef } from "react"
import { useQuery } from "@tanstack/react-query"

const loadKakaoMapScript = () => {
  return new Promise((resolve, reject) => {
    if (window.kakao && window.kakao.maps) {
      resolve(window.kakao)
      return
    }
    const script = document.createElement("script")
    script.src = `https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${"830555c3e8786f3b05edfbe3f056b4c8"}`
    script.onload = () => resolve(window.kakao)
    script.onerror = () => reject(new Error("에러다에러"))
    document.head.appendChild(script)
  })
}

const useKakaoMap = (latitude, longitude) => {
  const mapContainerRef = useRef(null)

  const queryResult = useQuery(
    ["KakaoMap", latitude, longitude],
    async () => {
      const kakao = await loadKakaoMapScript()
      kakao.maps.load(() => {
        if (mapContainerRef.current) {
          const mapOption = {
            center: new kakao.maps.LatLng(latitude, longitude),
            level: 3,
          }
          return new kakao.maps.Map(mapContainerRef.current, mapOption)
        }
      })
    },
    {
      enabled: !!latitude && !!longitude,
      retry: false,
      refetchOnWindowFocus: false,
    }
  )
  return { mapContainerRef, ...queryResult }
}

const KakaoMap = ({ longitude, latitude }) => {
  const { mapContainerRef, isLoading, isError, error } = useKakaoMap(
    latitude,
    longitude
  )

  if (isLoading) return <div>로딩중이지렁</div>
  if (isError) return <div>Error : {error.mesage}</div>

  return (
    <div ref={mapContainerRef} style={{ width: "100%", heigh: "400px" }}></div>
  )
}

export default KakaoMap
