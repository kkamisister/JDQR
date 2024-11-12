import axiosInstance from "./axiosInstance"

export const fetchRestaurants = async ({
  minLat,
  maxLat,
  minLng,
  maxLng,
  people,
  together,
}) => {
  try {
    const response = await axiosInstance.get("/map/restaurant", {
      params: { minLat, maxLat, minLng, maxLng, people, together },
    })
    return response.data.data
  } catch (error) {
    console.error("식당 정보 조회를 실패해버렸지....너는....:", error)
    throw new Error("식당 정보를 가져오는 중 오류가 발생했습니다.")
  }
}

export const fetchRestaurantDetail = async (restaurantId) => {
  try {
    const response = await axiosInstance.get(`/map/restaurant/${restaurantId}`)
    return response.data.data
  } catch (error) {
    console.error("식당 상세 정보 조회를 실패해버렸지...너는...:", error)
  }
}
