import axiosInstance from './axiosInstance';

// 로그인 관련 API
// React-Query 단에서 Mutate가 요구되는 API들(POST, DELETE)은 Promise를 반환하도록
// 그 외, fetch만 필요한 API들(GET)은 실제 API 응답의 data 맴버 내 실제 데이터를 반환

/**
 * 카카오 OAuth 코드로 AccessToken 및 사용자명 반환
 * @param {String} code - code
 * @returns {Object} -API 문서 참조
 */
export const fetchLoginInfoByCode = async code => {
	try {
		const response = await axiosInstance.post(`/owner/login`, {
			code: code,
		});
		const data = response.data;
		const accessToken = data.data.authToken.accessToken;
		console.log(accessToken);
		sessionStorage.setItem('accessToken', accessToken);

		// 받은 API Key로 axios instance에 Authorization 설정
		axiosInstance.defaults.headers.common[
			'Authorization'
		] = `Bearer ${accessToken}`;
		console.log(axiosInstance.defaults.headers.common['Authorization']);
		sessionStorage.setItem('restaurantName', data.data.restaurantName);
		return response.data;
	} catch (error) {
		// 에러가 발생했지만 response가 있는 경우, response의 데이터를 반환
		if (error.response && error.response.data) {
			return error.response.data;
		}

		// response가 없는 경우, 에러를 그대로 throw
		throw error;
	}
};

/**
 * 로그아웃
 * @returns {Boolean} -API 문서 참조
 */
export const logoutUser = async () => {
	const response = await axiosInstance.get(`/owner/logout`);
	return response.data.status === 200;
};
