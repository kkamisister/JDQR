import React from 'react';
import { Stack, Button } from '@mui/material';
import { colors } from 'constants/colors';
const MenuCardButton = ({ children, sx, onClick, direction = 'row' }) => {
	return (
		<Button
			onClick={onClick}
			direction={direction}
			sx={{
				backgroundColor: colors.background.white, // 박스 배경색 설정
				boxShadow: '0 3px 3px rgba(0,0,0,0.2)',
				borderRadius: '20px',
				padding: '20px',
				...sx,
				color: colors.text.main,
			}}>
			{children}
		</Button>
	);
};

export default MenuCardButton;
