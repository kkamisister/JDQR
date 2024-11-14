import React from 'react';
import { Box, Typography, TextField } from '@mui/material';
import { colors } from 'constants/colors';
const SubtitleTextField = ({ title, value, setValue, onKeyDown, sx }) => {
	return (
		<Box sx={{ width: '100%', ...sx }}>
			<Typography
				variant="subtitle1"
				sx={{ color: colors.text.main, fontWeight: 'bold' }}>
				{title}
			</Typography>
			<TextField
				sx={{ width: '100%' }}
				size="small"
				value={value}
				onKeyDown={onKeyDown}
				onChange={e => {
					setValue(e.target.value);
				}}
			/>
		</Box>
	);
};

export default SubtitleTextField;
