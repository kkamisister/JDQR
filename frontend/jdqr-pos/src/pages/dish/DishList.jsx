import React from 'react';
import DishListItem from './DishListItem';
import { Box } from '@mui/material';
const DishList = () => {
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
			<DishListItem
				name="감자탕(중)"
				price={32000}
				tags={['인기메뉴', '대표메뉴']}
			/>
			<DishListItem
				name="감자탕(중)"
				price={32000}
				tags={['인기메뉴', '대표메뉴']}
			/>
			<DishListItem
				name="감자탕(중)"
				price={32000}
				tags={['인기메뉴', '대표메뉴']}
			/>
			<DishListItem
				name="감자탕(중)"
				price={32000}
				tags={['인기메뉴', '대표메뉴']}
			/>
			<DishListItem
				name="감자탕(중)"
				price={32000}
				tags={['인기메뉴', '대표메뉴']}
			/>
			<DishListItem
				name="감자탕(중)"
				price={32000}
				tags={['인기메뉴', '대표메뉴']}
			/>
		</Box>
	);
};

export default DishList;
