import React from 'react';
import { Box, Stack, Divider } from '@mui/material';
import { colors } from 'constants/colors';
const OrderDetailBox = ({ table }) => {
	const totalPrice = table.dishes.reduce(
		(acc, dish) => acc + dish.price * dish.quantity,
		0
	);
	return (
		<Stack
			spacing={1.5}
			sx={{
				borderRadius: '10px',
				width: '350px',
				padding: '20px',
				backgroundColor: colors.background.primary,
			}}>
			<Box sx={{ fontSize: '30px', fontWeight: 'bold' }}>상세 주문 정보</Box>
			<Box
				sx={{
					fontSize: '25px',
					fontWeight: 'bold',
				}}>{`${table.name} (${table.people}인)`}</Box>
			<Stack spacing={0.5}>
				{table.dishes.map(dish => (
					<Stack
						sx={{
							fontSize: '20px',
							justifyContent: 'space-between',
							alignItems: 'center',
						}}
						direction="row">
						<Box>{`${dish.dishName} x${dish.quantity}`}</Box>
						<Box>{(dish.price * dish.quantity).toLocaleString()} 원</Box>
					</Stack>
				))}
			</Stack>
			<Stack spacing={1}>
				<Divider />
				<Stack
					sx={{
						fontSize: '20px',
						justifyContent: 'space-between',
						alignItems: 'center',
					}}
					direction="row">
					<Box>총계</Box>
					<Box>{totalPrice.toLocaleString()} 원</Box>
				</Stack>
			</Stack>
		</Stack>
	);
};

export default OrderDetailBox;
