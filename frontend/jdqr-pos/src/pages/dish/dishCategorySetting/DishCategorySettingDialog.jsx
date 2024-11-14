import { useState } from 'react';
import {
	Dialog,
	Stack,
	Box,
	Button,
	Typography,
	TextField,
	Chip,
	darken,
} from '@mui/material';
import { colors } from 'constants/colors';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import {
	fetchDishCategoryList,
	addDishCategory,
	deleteDishCategory,
} from 'utils/apis/dish';
import { enqueueSnackbar } from 'notistack';
const DishCategorySettingDialog = ({ open, onClose }) => {
	const queryClient = useQueryClient();

	const [category, setCategory] = useState('');

	const [isComposing, setIsComposing] = useState(false);

	const handleCompositionStart = () => {
		setIsComposing(true);
	};

	const handleCompositionEnd = () => {
		setIsComposing(false);
	};

	const { isPending, data: categoryList } = useQuery({
		queryKey: ['categoryList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchDishCategoryList(),
	});

	const handleAddDishCategory = async category => {
		await addDishCategory({ dishCategoryName: category });
		queryClient.invalidateQueries('dishList');
		enqueueSnackbar('카테고리를 추가했어요', { variant: 'success' });
	};

	const handleDeleteDishCategory = async dishCategoryId => {
		await deleteDishCategory({ dishCategoryId });
		queryClient.invalidateQueries('dishList');
		enqueueSnackbar('카테고리를 제거했어요', { variant: 'error' });
	};

	const handleEnterKeyPress = async event => {
		if (event.key === 'Enter' && !isComposing && category.trim() !== '') {
			event.preventDefault(); // 기본 동작 방지 (필요한 경우)
			await handleAddDishCategory(category);
			setCategory('');
		}
	};

	return (
		<Dialog maxWidth={'sm'} fullWidth={true} onClose={onClose} open={open}>
			<Stack spacing={2} direction="column" sx={{ margin: '20px' }}>
				{/* Dialog 타이틀 */}
				<Box sx={{ fontSize: '40px', fontWeight: 'bold' }}>
					{'카테고리 설정'}
				</Box>
				<Stack direction="row" sx={{ width: 'fit-content' }} spacing={1}>
					{categoryList &&
						categoryList.data.dishCategories.map(_category => (
							<Chip
								label={_category.dishCategoryName}
								onDelete={() => {
									handleDeleteDishCategory(_category.dishCategoryId);
								}}
							/>
						))}
				</Stack>

				<Stack>
					<Typography
						variant="subtitle1"
						sx={{ color: colors.text.main, fontWeight: 'bold' }}>
						카테고리명
					</Typography>
					<Stack direction="row" spacing={1}>
						<TextField
							sx={{ width: '50%' }}
							size="small"
							value={category}
							onKeyDown={handleEnterKeyPress}
							onCompositionStart={handleCompositionStart}
							onCompositionEnd={handleCompositionEnd}
							onChange={event => {
								setCategory(() => event.target.value);
							}}
						/>
						<Button
							size="small"
							disableElevation
							variant="contained"
							type="button" // Add this line
							sx={{
								backgroundColor: colors.point.blue, // 원하는 hex 값으로 배경색 설정
								'&:hover': {
									backgroundColor: darken(colors.point.blue, 0.2), // hover 시 색상도 설정 가능
								},
							}}
							onClick={() => {
								handleAddDishCategory(category);
							}}>
							카테고리 추가
						</Button>
					</Stack>
				</Stack>
			</Stack>
		</Dialog>
	);
};

export default DishCategorySettingDialog;
