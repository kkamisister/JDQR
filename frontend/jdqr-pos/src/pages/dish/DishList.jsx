import { useEffect, useState } from 'react';
import DishListItem from './DishListItem';
import { Box } from '@mui/material';
import DishEditDialog from './dishSetting/DishEditDialog';
const DishList = ({ dishItems, categoryId }) => {
	/**
	 * 메뉴 설정 Dialog 관련 상태 처리
	 */
	const [dishDialogOpen, setDishDialogOpen] = useState(false);
	const handleDishDialogOpen = dish => {
		if (dish == null) {
			return;
		}
		console.log(dish);
		setSelectedDish(() => dish);
		setDishDialogOpen(() => true);
	};

	const handleDishDialogClose = () => {
		setDishDialogOpen(() => false);
	};
	const [selectedDish, setSelectedDish] = useState(null);
	useEffect(() => {
		console.log(categoryId);
	}, [categoryId]);
	return (
		<Box
			direction="row"
			spacing={1}
			sx={{
				width: '100%',
				display: 'flex',
				flexWrap: 'wrap', // 너비 초과 시 다음 줄로 이동
				gap: 3, // 컴포넌트 간의 간격
				justifyContent: 'flex-start',
				alignItems: 'flex-start',
			}}>
			{dishItems.map((dish, idx) => (
				<DishListItem
					key={`${dish.dishName}-idx`}
					name={dish.dishName}
					price={dish.price}
					tags={dish.tags}
					onClick={() => {
						handleDishDialogOpen(dish);
					}}
				/>
			))}
			<DishEditDialog
				dishInfo={{ ...selectedDish, dishCategoryId: categoryId }}
				open={dishDialogOpen}
				onClose={handleDishDialogClose}
			/>
		</Box>
	);
};

export default DishList;
