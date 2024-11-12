import React from 'react';
import { Box, Typography, TextField } from '@mui/material';
import { colors } from 'constants/colors';
const SubtitleTextField = ({ title, value, setValue }) => {
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
				value={value}
				onChange={e => {
					setValue(e.target.value);
				}}
			/>
		</Box>
	);
};

export default SubtitleTextField;
