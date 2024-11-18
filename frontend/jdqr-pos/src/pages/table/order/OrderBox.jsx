import { Stack, Box } from '@mui/material';
import { colors } from 'constants/colors';
import { darken } from '@mui/system';
import React from 'react';

const OrderBox = ({ tableName, order, color, people }) => {
	return (
		<Stack
			spacing={1}
			sx={{
				color: colors.text.white,
				padding: '20px',
				backgroundColor: color,
				borderRadius: '10px',
				cursor: 'pointer',
				transition: 'background-color 0.3s ease', // 배경색 전환을 부드럽게
				'&:hover': {
					backgroundColor: darken(color, 0.2), // 기존 색상보다 10% 어두운 색으로 설정
				},
			}}>
			<Box sx={{ fontSize: '25px', fontWeight: 'bold' }}>{tableName}</Box>
			<Stack spacing={0.5}>
				{order.map((dish, index) => (
					<Box
						key={index}
						sx={{
							fontSize: '20px',
						}}>{`· ${dish.dishName} x${dish.quantity}`}</Box>
				))}
			</Stack>
			<Box
				sx={{
					fontSize: '15px',
					fontWeight: 'bold',
				}}>{`${people}인 테이블`}</Box>
		</Stack>
	);
};

export default OrderBox;
