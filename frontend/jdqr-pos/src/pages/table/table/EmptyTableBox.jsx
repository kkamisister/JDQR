import { useState, useEffect } from 'react';
import { Box, Stack, TextField, Typography } from '@mui/material';
import { colors } from 'constants/colors';
import TableRestaurantIcon from '@mui/icons-material/TableRestaurant';
const EmptyTableBox = () => {
	return (
		<Stack
			spacing={1.5}
			direction="column"
			sx={{
				borderRadius: '10px',
				width: '350px',
				padding: '20px',
				backgroundColor: colors.background.primary,
				height: '100%',
				justifyContent: 'center',
				alignItems: 'center',
			}}>
			<TableRestaurantIcon
				sx={{ color: colors.text.sub1, fontSize: '100px' }}
			/>
			<Box sx={{ color: colors.text.sub1 }}>테이블을 선택하세요</Box>
		</Stack>
	);
};

export default EmptyTableBox;
