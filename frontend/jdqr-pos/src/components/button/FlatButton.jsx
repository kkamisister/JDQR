import React from 'react';
import { Button } from '@mui/material';
import { darken } from '@mui/system';
import { colors } from 'constants/colors';
const FlatButton = ({
	text,
	color,
	fontColor = colors.text.white,
	sx,
	onClick,
}) => {
	return (
		<Button
			variant="contained"
			disableElevation
			size="large"
			sx={{
				borderRadius: '10px',
				backgroundColor: color, // 원하는 hex 값으로 배경색 설정
				'&:hover': {
					backgroundColor: darken(color, 0.2), // hover 시 색상도 설정 가능
				},
				color: fontColor,
				fontSize: '20px',
				fontWeight: '600',
				...sx,
			}}
			onClick={onClick}>
			{text}
		</Button>
	);
};

export default FlatButton;
