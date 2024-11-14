import React, { useState, useEffect } from 'react';
import { Dialog, Stack, Box, Button, ButtonGroup, Chip } from '@mui/material';
import FlatButton from 'components/button/FlatButton';
import { colors } from 'constants/colors';
import SubtitleTextField from 'components/input/SubtitleTextField';
import ImageBox from 'components/common/ImageBox';
import EmptyImageBox from 'components/empty/EmptyImageBox';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { addDish, editDish, fetchDishCategoryList } from 'utils/apis/dish';
import SubtitleSelector from 'components/input/SubtitleSelector';

const DishSettingDialog = ({
	open,
	onClose,
	isEdit = false,
	dishInfo = {
		dishName: '새로운 상품명',
		dishCategoryId: null,
		dishCategoryName: '카테고리명',
		optionIds: [],
		price: 0,
		description: '상품 설명',
		image: null,
		tags: [],
	},
}) => {
	const queryClient = useQueryClient();
	const [name, setName] = useState(dishInfo.dishName);
	const [description, setDescription] = useState(dishInfo.description);
	const [price, setPrice] = useState(dishInfo.price);
	const [categoryName, setCategoryName] = useState(dishInfo.dishCategoryName);
	const [categoryId, setCategoryId] = useState(dishInfo.dishCategoryId);
	const [tagList, setTagList] = useState(dishInfo.tags);
	const [settingMenu, setSettingMenu] = useState(0);
	const [tag, setTag] = useState('');
	const { isPending, data: categoryList } = useQuery({
		queryKey: ['categoryList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchDishCategoryList(),
	});

	const [imageSrc, setImageSrc] = useState(null);

	const saveCurrentDish = async () => {
		if (isEdit) {
			await editDish({});
			queryClient.invalidateQueries('dishList');
		} else {
			await addDish(
				{
					dishName: name,
					dishCategoryId: categoryId,
					dishCategoryName: categoryName,
					optionIds: [],
					price: price,
					description: description,
					image: '',
					tagIds: tagList,
				},
				imageSrc
			);
			queryClient.invalidateQueries('dishList');
		}
	};

	// 이미지 파일을 읽고 미리보기 설정
	const handleImageUpload = event => {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = () => {
				setImageSrc(reader.result); // base64 URL로 설정
			};
			reader.readAsDataURL(file);
		}
	};

	const handleEnterKeyPress = event => {
		if (event.key === 'Enter') {
			event.preventDefault(); // 기본 동작 방지 (필요한 경우)

			setTagList(prevItems => [...prevItems, tag]); // 함수 호출
		}
	};

	useEffect(() => {
		console.log(tagList);
	}, [tagList]);
	return (
		<Dialog maxWidth={'md'} fullWidth={true} onClose={onClose} open={open}>
			<Stack spacing={2} direction="column" sx={{ margin: '20px' }}>
				{/* Dialog 타이틀 */}
				<Box sx={{ fontSize: '40px', fontWeight: 'bold' }}>
					{isEdit && '상품 정보 수정'}
					{!isEdit && '상품 정보 추가'}
				</Box>
				{/* 내부 Content */}
				<Stack
					direction="row"
					spacing={8}
					sx={{
						width: '100%',
						justifyContent: 'center',
					}}>
					{/* 사진 추가, 저장 및 삭제 버튼 */}
					<Stack direction="column" spacing={2}>
						{!dishInfo.image && !imageSrc && (
							<EmptyImageBox sx={{ width: '300px', height: '300px' }} />
						)}
						{dishInfo.image && (
							<ImageBox
								src={dishInfo.image}
								alt={'음식 이미지'}
								sx={{ width: '300px', height: '300px' }}
							/>
						)}
						{imageSrc && (
							<ImageBox
								src={imageSrc}
								alt={'음식 이미지'}
								sx={{ width: '300px', height: '300px' }}
							/>
						)}
						<input
							type="file"
							accept="image/*"
							onChange={handleImageUpload}
						/>
						<Stack direction="row">
							<FlatButton
								text="상품 저장"
								color={colors.point.blue}
								onClick={() => {
									saveCurrentDish();
								}}
							/>
							{isEdit && (
								<FlatButton text="상품 삭제" color={colors.point.red} />
							)}
						</Stack>
					</Stack>
					<Stack direction="column" spacing={1} sx={{ minWidth: '350px' }}>
						<ButtonGroup aria-label="상품 정보 수정 옵션 버튼 그룹">
							<Button
								size="large"
								disableElevation
								onClick={() => {
									setSettingMenu(0);
								}}
								sx={
									settingMenu === 0 && {
										backgroundColor: colors.point.blue, // 버튼 배경 색상 (HEX 값)
										color: '#fff', // 텍스트 색상 (HEX 값)
										'&:hover': {
											backgroundColor: '#2980b9', // 호버 시 색상
										},
									}
								}>
								{'상품 정보'}
							</Button>
							<Button
								size="large"
								disableElevation
								variant={settingMenu === 1 ? 'contained' : 'outlined'}
								onClick={() => {
									setSettingMenu(1);
								}}
								sx={
									settingMenu === 1 && {
										backgroundColor: colors.point.blue, // 버튼 배경 색상 (HEX 값)
										color: '#fff', // 텍스트 색상 (HEX 값)
										'&:hover': {
											backgroundColor: '#2980b9', // 호버 시 색상
										},
									}
								}>
								{'상품 옵션'}
							</Button>
						</ButtonGroup>

						{settingMenu === 0 && (
							<Stack spacing={1}>
								<SubtitleTextField
									title="상품명"
									value={name}
									setValue={setName}
								/>
								<SubtitleSelector
									title="카테고리"
									value={categoryId ? categoryId : ''}
									selectList={categoryList?.data.dishCategories.map(
										item => ({
											id: item.dishCategoryId,
											name: item.dishCategoryName,
										})
									)}
									setValue={setCategoryId}
								/>
								<SubtitleTextField
									title="가격(원)"
									value={price}
									setValue={setPrice}
								/>

								<SubtitleTextField
									title="상품 설명"
									value={description}
									setValue={setDescription}
								/>

								<SubtitleTextField
									title="태그"
									value={tag}
									setValue={setTag}
									onKeyDown={handleEnterKeyPress}
								/>
								<Stack direction="row">
									{tagList.map((_tag, index) => (
										<Chip
											label={_tag}
											key={`_tag-${_tag}-${index}`}
										/>
									))}
								</Stack>
							</Stack>
						)}
					</Stack>
				</Stack>
			</Stack>
		</Dialog>
	);
};

export default DishSettingDialog;
