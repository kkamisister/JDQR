import React from 'react';
import { Box, Typography, Stack } from '@mui/material';
import { colors } from 'constants/colors';
import EditColorPickerBox from './EditColorPickerBox';
const pickerRowCount = 5;
const EditColorBox = ({ currentColorCode, setTableColor }) => {
	return (
		<Box>
			<Typography
				variant="subtitle1"
				sx={{ color: colors.text.main, fontWeight: 'bold' }}>
				색상 선택
			</Typography>
			<Stack
				direction="row"
				spacing={1}
				sx={{ justifyContent: 'space-between', alignItems: 'center' }}>
				{colors.table.map((colorCode, idx) => {
					return (
						<EditColorPickerBox
							color={colorCode}
							isSelected={currentColorCode === colorCode}
							onClick={() => {
								setTableColor(colorCode);
							}}
						/>
					);
				})}
			</Stack>
		</Box>
	);
};

export default EditColorBox;
