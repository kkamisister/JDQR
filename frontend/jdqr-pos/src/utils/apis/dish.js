import axiosInstance from './axiosInstance';

// 음식 메뉴 관련 API
// React-Query 단에서 Mutate가 요구되는 API들(POST, DELETE)은 Promise를 반환하도록
// 그 외, fetch만 필요한 API들(GET)은 실제 API 응답의 data 맴버 내 실제 데이터를 반환

/**
 * 전체 메뉴 조회
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const fetchDishList = async keyword => {
	const response = await axiosInstance.get('/owner/dish');
	return response.data;
};

/**
 * 상세 메뉴 정보 조회
 * @param {Number} dishId - dishId
 * @returns {Promise} - API 문서 참조
 */
export const fetchDishDetailByDishId = async dishId => {
	return axiosInstance.get('/owner/dish', { dishId });
};

/**
 * 메뉴 추가
 * @typedef {String} dishName - 상품명
 * @typedef {Number} dishCategoryId - 카테고리ID
 * @typedef {String} dishCategoryName - 카테고리명
 * @typedef {Array} optionIds - 옵션ID 배열 (당도/음료 등)
 * @typedef {Number} price - 가격(원)
 * @typedef {Number} description - 상품 설명
 * @typedef {String} image - 이미지
 * @typedef {Array} tagIds - 테그ID 배열 (시그니쳐, 인기메뉴 등)
 * @param {{dishName, dishCategoryId, dishCategoryName, optionIds, price, description, image, tagIds}}
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const addDish = async ({
	dishName,
	dishCategoryId,
	dishCategoryName,
	optionIds,
	price,
	description,
	image,
	tagIds,
}) => {
	const response = await axiosInstance.post(`/owner/dish`, {
		dishName,
		dishCategoryId,
		dishCategoryName,
		optionIds,
		price,
		description,
		image,
		tagIds,
	});
	return response.data;
};

/**
 * 상품 삭제
 * @param {Number} - dishId
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const deleteDish = async ({ dishId }) => {
	const response = await axiosInstance.delete(`/owner/dish?dishId=${dishId}`);
	return response.data;
};

/**
 * 메뉴 추가
 * @typedef {Number} dishId - 상품ID
 * @typedef {String} dishName - 상품명
 * @typedef {Number} dishCategoryId - 카테고리ID
 * @typedef {String} dishCategoryName - 카테고리명
 * @typedef {Array} optionIds - 옵션ID 배열 (당도/음료 등)
 * @typedef {Number} price - 가격(원)
 * @typedef {Number} description - 상품 설명
 * @typedef {String} image - 이미지
 * @typedef {Array} tagIds - 테그ID 배열 (시그니쳐, 인기메뉴 등)
 * @param {{dishName, dishCategoryId, dishCategoryName, optionIds, price, description, image, tagIds}}
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const editDish = async ({
	dishId,
	dishName,
	dishCategoryId,
	dishCategoryName,
	optionIds,
	price,
	description,
	image,
	tagIds,
}) => {
	const response = await axiosInstance.put(`/owner/dish?dishId=${dishId}`, {
		dishName,
		dishCategoryId,
		dishCategoryName,
		optionIds,
		price,
		description,
		image,
		tagIds,
	});
	return response.data;
};

/**
 * 전체 메뉴 카테고리 조회
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const fetchDishCategoryList = async () => {
	const response = await axiosInstance.get(`/owner/dish/category`);
	return response.data;
};

/**
 * 메뉴 카테고리 삭제
 * @param {Number} - dishId
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const deleteDishCategory = async ({ dishCategoryId }) => {
	const response = await axiosInstance.delete(
		`/owner/dish?dishCategoryId=${dishCategoryId}`
	);
	return response.data;
};

/**
 * 메뉴 카테고리 추가
 * @typedef {String} dishCategoryName - 카테고리명
 * @param {{dishCategoryName}}
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const addDishCategory = async ({ dishCategoryName }) => {
	const response = await axiosInstance.put(`/owner/dish/category`, {
		dishCategoryName,
	});
	return response.data;
};

/**
 * 옵션 추가
 * @typedef {Number} optionName - 옵션 이름
 * @typedef {String} maxChoiceCount - 최대 선택 갯수
 * @typedef {Number} isMandatory - 옵션 필수 여부
 * @typedef {Array} choices - 옵션 내부 선택 리스트
 * @typedef {{choiceName:String, price:Number}} choice - 옵션 내부 선택
 * @param {{optionName, maxChoiceCount, isMandatory, choices}}
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const addDishOption = async ({
	optionName,
	maxChoiceCount,
	isMandatory,
	choices,
}) => {
	const response = await axiosInstance.put(`/owner/option`, {
		optionName,
		maxChoiceCount,
		isMandatory,
		choices,
	});
	return response.data;
};

/**
 * 전체 옵션 조회
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const fetchDishOptionList = async () => {
	const response = await axiosInstance.get(`/owner/option/all`);
	return response.data;
};

/**
 * 상세 옵션 조회
 * @param {Number} optionId - optionId
 * @returns {Object} - Response 내 data 객체, API 문서 참조
 */
export const fetchDishOptionDetail = async ({ optionId }) => {
	const response = await axiosInstance.get(`/owner/option/${optionId}`);
	return response.data;
};
