import { Box, darken } from '@mui/material';
import React from 'react';
import { colors } from 'constants/colors';

const DishOptionListItem = ({ optionName, onClick, selected = false }) => {
	return (
		<Box
			sx={{
				backgroundColor: selected
					? darken(colors.background.box, 0.2)
					: colors.background.primary,
				padding: '20px',
				fontSize: '20px',
				borderRadius: '10px',
				'&:hover': {
					backgroundColor: darken(colors.background.box, 0.1),
				},
				transition: 'all 0.3s',
				cursor: 'pointer',
			}}
			onClick={onClick}>
			{optionName}
		</Box>
	);
};

export default DishOptionListItem;
