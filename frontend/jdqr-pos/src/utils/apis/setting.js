import axiosInstance from './axiosInstance';

// 기타 설정 관련 API
// React-Query 단에서 Mutate가 요구되는 API들(POST, DELETE)은 Promise를 반환하도록
// 그 외, fetch만 필요한 API들(GET)은 실제 API 응답의 data 맴버 내 실제 데이터를 반환

/**
 * 사업장 등록
 * @typedef {String} restaurantName - 사업장 이름
 * @typedef {String} address - 주소
 * @typedef {String} phoneNumber - 전화번호
 * @typedef {String} industry - 음식점 카테고리
 * @typedef {String} registrationNumber - 사업자등록번호
 * @typedef {Number} image - ????
 * @typedef {Number} lat - 위도
 * @typedef {Number} lng - 경도
 * @param {{restaurantName, address, phoneNumber, industry, registrationNumber, lat, lng}}
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const registerRestaurant = async ({
	restaurantName,
	address,
	phoneNumber,
	industry,
	registrationNumber,
	lat,
	lng,
}) => {
	const response = await axiosInstance.post('/owner/restaurant', {
		restaurantName,
		address,
		phoneNumber,
		industry,
		registrationNumber,
		lat,
		lng,
	});
	return response.data;
};

/**
 * 사업장 조회
 * @returns {Promise} - API 문서 참조
 */
export const fetchRestaurant = async () => {
	const response = await axiosInstance.get(`/owner/restaurant`);
	return response.data;
};

/**
 * 영업 여부 전환
 * @returns {Promise} - API 문서 참조
 */
export const changeBusinessHoursRestaurant = async ({ open }) => {
	return await axiosInstance.put(`/owner/restaurant/status`, { open });
};

/**
 * 영업 여부 조회
 * @returns {Promise} - API 문서 참조
 */
export const fetchBusinessHoursRestaurant = async () => {
	const response = await axiosInstance.get(`/owner/restaurant/status`);
	return response.data;
};
