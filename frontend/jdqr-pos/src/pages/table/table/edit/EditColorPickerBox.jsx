import React from 'react';
import { IconButton } from '@mui/material';
import CheckIcon from '@mui/icons-material/Check';
import { colors } from 'constants/colors';
const EditColorPickerBox = ({ color, isSelected, onClick }) => {
	return (
		<IconButton
			onClick={onClick}
			sx={{
				padding: '0px',
				minWidth: '30px',
				width: '30px',
				height: '30px',
				backgroundColor: color,
			}}>
			{isSelected && <CheckIcon style={{ color: colors.text.white }} />}
		</IconButton>
	);
};

export default EditColorPickerBox;
