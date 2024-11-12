import axiosInstance from './axiosInstance';

// 테이블 및 주문 정보 관련 API
// React-Query 단에서 Mutate가 요구되는 API들(POST, DELETE)은 Promise를 반환하도록
// 그 외, fetch만 필요한 API들(GET)은 실제 API 응답의 data 맴버 내 실제 데이터를 반환

/**
 * 테이블 생성
 * @typedef {String} name - 테이블명
 * @typedef {String} color - 테이블 색상
 * @typedef {Number} people - 인원 수
 * @param {{name, color, people}}
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const addTable = async ({ name, color, people }) => {
	const response = await axiosInstance.post('/owner/table', {
		name,
		color,
		people,
	});
	return response.data;
};

/**
 * 테이블 삭제
 * @param {Number} tableId - tableId
 * @returns {Promise} - API 문서 참조
 */
export const removeTable = async ({ tableId }) => {
	return axiosInstance.delete(`/owner/table?tableId=${tableId}`);
};

/**
 * 테이블 수정
 * @typedef {Number} tableId - tableId
 * @typedef {String} name - 테이블명
 * @typedef {String} color - 테이블 색상
 * @typedef {Number} people - 인원 수
 * @param {{name, color, people}}
 * @returns {Promise} - API 문서 참조
 */
export const editTable = async ({ tableId, name, color, people }) => {
	return axiosInstance.put(`/onwer/table`, {
		tableId,
		name,
		color,
		people,
	});
};

/**
 * 테이블 QR URL 생성
 * @param {Number} tableId - tableId
 * @returns {Promise} - API 문서 참조
 */
export const renewTableUrl = async ({ tableId }) => {
	return axiosInstance.post(`/owner/table/qr?tableId=${tableId}`);
};

/**
 * 전체 테이블 조회
 * @returns {Object} - API 문서 참조
 */
export const fetchTables = async () => {
	const response = await axiosInstance.get('/owner/table');
	return response.data;
};

/**
 * 테이블 상세 조회
 * @param {Number} tableId - tableId
 * @returns {Promise} - API 문서 참조
 */
export const fetchTableDetail = async ({ tableId }) => {
	const response = await axiosInstance.get(`/onwer/table?tableId=${tableId}`);
	return response.data.data;
};
