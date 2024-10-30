import React from 'react';
import MenuCardButton from './MenuCardButton';
import { useNavigate } from 'react-router-dom';
import { Stack, Box, Typography } from '@mui/material';
const SettingCardButton = ({ title, icon, path }) => {
	const navigate = useNavigate();
	return (
		<MenuCardButton
			onClick={() => {
				navigate(path);
			}}>
			<Stack
				spacing={2}
				sx={{
					justifyContent: 'center',
					alignItems: 'center',
					width: '150px',
					height: '150px',
				}}>
				<Box sx={{ width: '100px', height: '100px' }}>
					<img
						style={{ width: '100%', height: '100%' }}
						src={icon}
						alt={title}
					/>
				</Box>
				<Typography variant="h6" sx={{ fontWeight: 'bold' }}>
					{title}
				</Typography>
			</Stack>
		</MenuCardButton>
	);
};

export default SettingCardButton;
