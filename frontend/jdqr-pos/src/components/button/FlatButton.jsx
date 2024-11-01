import React from 'react';
import { Button } from '@mui/material';
import { darken } from '@mui/system';
const FlatButton = ({ text, color }) => {
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
				fontSize: '20px',
				fontWeight: '500',
			}}>
			{text}
		</Button>
	);
};

export default FlatButton;
