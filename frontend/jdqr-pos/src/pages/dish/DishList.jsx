import React from 'react';
import DishListItem from './DishListItem';
import { Box } from '@mui/material';
const DishList = ({ dishItems }) => {
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
				/>
			))}
		</Box>
	);
};

export default DishList;
