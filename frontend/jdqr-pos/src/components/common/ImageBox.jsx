import React from 'react';
import { Box } from '@mui/material';
const ImageBox = ({ src, alt, sx, borderRadius }) => {
	return (
		<Box sx={{ ...sx }}>
			<img
				src={src}
				alt={alt}
				style={{
					overflow: 'hidden',
					width: '100%',
					height: '100%',
					borderRadius: borderRadius,
				}}
			/>
		</Box>
	);
};

export default ImageBox;
