import React from 'react';
import { Box, Typography, TextField } from '@mui/material';
import { colors } from 'constants/colors';
const SubtitleTextField = ({ title, placeholder }) => {
	return (
		<Box sx={{ width: '100%' }}>
			<Typography
				variant="subtitle1"
				sx={{ color: colors.text.main, fontWeight: 'bold' }}>
				{title}
			</Typography>
			<TextField
				sx={{ width: '100%' }}
				size="small"
				placeholder={placeholder}
			/>
		</Box>
	);
};

export default SubtitleTextField;
