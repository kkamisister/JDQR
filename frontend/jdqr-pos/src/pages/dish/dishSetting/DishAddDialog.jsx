import React, { useState, useEffect } from 'react';
import {
	Dialog,
	Stack,
	Box,
	Button,
	ButtonGroup,
	Chip,
	styled,
	darken,
} from '@mui/material';
import FlatButton from 'components/button/FlatButton';
import { colors } from 'constants/colors';
import SubtitleTextField from 'components/input/SubtitleTextField';
import ImageBox from 'components/common/ImageBox';
import EmptyImageBox from 'components/empty/EmptyImageBox';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import {
	addDish,
	editDish,
	fetchDishCategoryList,
	fetchDishOptionList,
} from 'utils/apis/dish';
import SubtitleSelector from 'components/input/SubtitleSelector';
import { enqueueSnackbar } from 'notistack';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import CheckIcon from '@mui/icons-material/Check';

const VisuallyHiddenInput = styled('input')({
	clip: 'rect(0 0 0 0)',
	clipPath: 'inset(50%)',
	height: 1,
	overflow: 'hidden',
	position: 'absolute',
	bottom: 0,
	left: 0,
	whiteSpace: 'nowrap',
	width: 1,
});
const DishAddDialog = ({
	open,
	onClose,
	isEdit = false,
	dishInfo = {
		dishName: '새로운 상품명',
		dishCategoryId: null,
		dishCategoryName: '카테고리명',
		options: [],
		price: 0,
		description: '상품 설명',
		image: '',
		tags: [],
	},
}) => {
	const queryClient = useQueryClient();
	const [newDishInfo, setNewDishInfo] = useState(dishInfo);

	const [settingMenu, setSettingMenu] = useState(0);
	const [tag, setTag] = useState('');
	const { isPending, data: categoryList } = useQuery({
		queryKey: ['categoryList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchDishCategoryList(),
	});
	const { isPending: isOptionPending, data: optionList } = useQuery({
		queryKey: ['optionList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchDishOptionList(),
	});
	const [isComposing, setIsComposing] = useState(false);

	const handleCompositionStart = () => {
		setIsComposing(true);
	};

	const handleCompositionEnd = () => {
		setIsComposing(false);
	};

	const handleTagDelete = removedTag => {
		setNewDishInfo(prev => {
			return {
				...prev,
				tags: prev.tags.filter(item => item !== removedTag),
			};
		});
	};

	const [imageSrc, setImageSrc] = useState(null);
	const [imageRawSrc, setImageRawSrc] = useState(null);
	const saveCurrentDish = async () => {
		await addDish(
			{
				...newDishInfo,
				optionIds: newDishInfo.options.map(option => option.optionId),
			},
			imageRawSrc
		);
		queryClient.invalidateQueries('dishList');
		enqueueSnackbar('메뉴를 추가했어요', { variant: 'success' });
		onClose();
	};

	// 이미지 파일을 읽고 미리보기 설정
	const handleImageUpload = event => {
		const file = event.target.files[0];
		if (file) {
			setImageRawSrc(file);
			const reader = new FileReader();
			reader.onload = () => {
				setImageSrc(reader.result); // base64 URL로 설정
			};
			reader.readAsDataURL(file);
		}
	};

	const handleEnterKeyPress = event => {
		if (
			event.key === 'Enter' &&
			!isComposing &&
			!event.nativeEvent.isComposing &&
			tag.trim() !== ''
		) {
			event.preventDefault(); // 기본 동작 방지 (필요한 경우)

			setNewDishInfo(prev => {
				return {
					...prev,
					tags: [...prev.tags, tag],
				};
			});
			setTag('');
		}
	};

	return (
		<Dialog maxWidth={'md'} fullWidth={true} onClose={onClose} open={open}>
			<Stack spacing={2} direction="column" sx={{ margin: '20px' }}>
				{/* Dialog 타이틀 */}
				<Box sx={{ fontSize: '40px', fontWeight: 'bold' }}>
					{'상품 정보 추가'}
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
						<Button
							component="label"
							role={undefined}
							variant="contained"
							tabIndex={-1}
							disableElevation
							sx={{
								borderRadius: '10px',
								backgroundColor: colors.point.blue, // 원하는 hex 값으로 배경색 설정
								'&:hover': {
									backgroundColor: darken(colors.point.blue, 0.2), // hover 시 색상도 설정 가능
								},
								fontSize: '20px',
								fontWeight: '600',
							}}
							startIcon={<CloudUploadIcon />}>
							이미지 업로드
							<VisuallyHiddenInput
								type="file"
								accept="image/*"
								onChange={handleImageUpload}
								multiple
							/>
						</Button>
						<Stack direction="row">
							<FlatButton
								text="상품 저장"
								color={colors.point.blue}
								onClick={() => {
									saveCurrentDish();
								}}
							/>
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
									settingMenu === 0
										? {
												backgroundColor: colors.point.blue, // 버튼 배경 색상 (HEX 값)
												color: '#fff', // 텍스트 색상 (HEX 값)
												'&:hover': {
													backgroundColor: '#2980b9', // 호버 시 색상
												},
										  }
										: {}
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
									settingMenu === 1
										? {
												backgroundColor: colors.point.blue, // 버튼 배경 색상 (HEX 값)
												color: '#fff', // 텍스트 색상 (HEX 값)
												'&:hover': {
													backgroundColor: '#2980b9', // 호버 시 색상
												},
										  }
										: {}
								}>
								{'상품 옵션'}
							</Button>
						</ButtonGroup>
						{settingMenu === 0 && (
							<Stack spacing={1} sx={{ width: '380px' }}>
								<SubtitleTextField
									type="text"
									title="상품명"
									value={newDishInfo.dishName}
									setValue={value => {
										setNewDishInfo(prev => {
											return {
												...prev,
												dishName: value,
											};
										});
									}}
								/>
								<SubtitleSelector
									title="카테고리"
									value={newDishInfo.dishCategoryId}
									selectList={categoryList?.data.dishCategories.map(
										item => ({
											id: item.dishCategoryId,
											name: item.dishCategoryName,
										})
									)}
									setValue={value => {
										setNewDishInfo(prev => {
											return {
												...prev,
												dishCategoryId: value,
											};
										});
									}}
								/>
								<SubtitleTextField
									type="number"
									title="가격(원)"
									value={newDishInfo.price}
									setValue={value => {
										setNewDishInfo(prev => {
											return {
												...prev,
												price: value,
											};
										});
									}}
								/>

								<SubtitleTextField
									type="text"
									title="상품 설명"
									value={newDishInfo.description}
									setValue={value => {
										setNewDishInfo(prev => {
											return {
												...prev,
												description: value,
											};
										});
									}}
								/>

								<SubtitleTextField
									type="text"
									title="태그"
									value={tag}
									setValue={setTag}
									onCompositionStart={handleCompositionStart}
									onCompositionEnd={handleCompositionEnd}
									onKeyDown={handleEnterKeyPress}
								/>
								<Stack direction="row" spacing={0.5}>
									{newDishInfo.tags?.map((_tag, index) => (
										<Chip
											label={_tag}
											key={`_tag-${_tag}-${index}`}
											onDelete={() => {
												handleTagDelete(_tag);
											}}
										/>
									))}
								</Stack>
							</Stack>
						)}
						{settingMenu === 1 && (
							<Box
								sx={{
									display: 'flex',
									flexWrap: 'wrap', // 너비를 초과하면 다음 줄로 넘어감
									gap: 2, // 간격 설정
									width: '380px', // 부모의 너비에 맞게 설정
								}}>
								{optionList.data.options.map((option, index) => (
									<Chip
										key={`option-chip-${index}`}
										label={option.optionName}
										variant="outlined"
										icon={
											newDishInfo.options.some(
												_option =>
													_option.optionId === option.optionId
											) && <CheckIcon />
										}
										onClick={() => {
											const isChecked = newDishInfo.options.some(
												_option =>
													_option.optionId === option.optionId
											);
											if (isChecked) {
												setNewDishInfo(prev => {
													return {
														...prev,
														options: prev.options.filter(
															_option =>
																_option.optionId !==
																option.optionId
														),
													};
												});
											} else {
												setNewDishInfo(prev => {
													return {
														...prev,
														options: [...prev.options, option],
													};
												});
											}
										}}
										clickable
									/>
								))}
							</Box>
						)}
					</Stack>
				</Stack>
			</Stack>
		</Dialog>
	);
};

export default DishAddDialog;
