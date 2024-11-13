import React from 'react';
import { Stack } from '@mui/material';
import { colors } from 'constants/colors';
import ImageNotSupportedIcon from '@mui/icons-material/ImageNotSupported';
const EmptyImageBox = ({ sx }) => {
	return (
		<Stack
			sx={{
				backgroundColor: colors.background.box,
				borderRadius: '10px',
				justifyContent: 'center',
				alignItems: 'center',
				...sx,
			}}>
			<ImageNotSupportedIcon fontSize="large" />
		</Stack>
	);
};

export default EmptyImageBox;
