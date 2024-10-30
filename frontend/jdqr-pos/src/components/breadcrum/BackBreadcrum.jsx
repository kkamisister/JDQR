import React from 'react';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { Stack, Typography } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import { useNavigate } from 'react-router-dom';
const BackBreadcrum = ({ text, sx, path }) => {
	const navigate = useNavigate();

	return (
		<Stack
			spacing={1}
			direction="row"
			sx={{
				position: 'absolute',
				top: '70px',
				left: '40px',
				justifyContent: 'center',
				alignItems: 'center',
				...sx,
			}}>
			<IconButton
				onClick={() => {
					navigate(path);
				}}>
				<ArrowBackIcon fontSize="large" />
			</IconButton>
			<Typography variant="h5" sx={{ fontWeight: 'bold' }}>
				{text}
			</Typography>
		</Stack>
	);
};

export default BackBreadcrum;
