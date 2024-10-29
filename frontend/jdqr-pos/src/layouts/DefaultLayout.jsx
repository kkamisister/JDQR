import React from 'react';
import { Box, Stack } from '@mui/material';
import { colors } from 'constants/colors';
const DefaultLayout = ({ children }) => {
	return (
		<Stack
			sx={{
				width: '100vw',
				height: '100vh',
				justifyContent: 'center',
				alignItems: 'center',
			}}>
			<Box
				sx={{
					position: 'fixed',
					aspectRatio: '1 / 1',
					top: '-224vh',
					height: '300vh',
					left: '-40vw',
					background:
						'radial-gradient(closest-side, #89d8d8 0%, rgba(49,130,246,0.15) 70%, rgba(255,255,255,0) 100%)',
					pointerEvents: 'none',
					zIndex: '-1',
				}}
			/>
			{children}
		</Stack>
	);
};

export default DefaultLayout;
