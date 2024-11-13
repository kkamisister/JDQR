import React from 'react';
import { Box, Typography, FormControl, Select, MenuItem } from '@mui/material';
import { colors } from 'constants/colors';
const SubtitleSelector = ({ title, selectList, value, setValue }) => {
	console.log({ title, selectList, value, setValue });
	return (
		<Box sx={{ width: '100%' }}>
			<Typography
				variant="subtitle1"
				sx={{ color: colors.text.main, fontWeight: 'bold' }}>
				{title}
			</Typography>
			<FormControl sx={{ width: '100%' }} size="small">
				<Select
					value={value}
					onChange={e => {
						console.log(e.target.value);
						setValue(e.target.value);
					}}
					displayEmpty
					inputProps={{ 'aria-label': title }}>
					{selectList &&
						selectList.map(select => (
							<MenuItem
								key={`${select.name}-${select.id}`}
								value={select.id}>
								{select.name}
							</MenuItem>
						))}
				</Select>
			</FormControl>
		</Box>
	);
};

export default SubtitleSelector;
