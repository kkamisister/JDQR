import React, { forwardRef } from 'react';
import { Stack, Box } from '@mui/material';
import RestaurantLogoSample from 'assets/images/RestaurantLogoSample.png';
import ImageBox from 'components/common/ImageBox';
import { colors } from 'constants/colors';
import QRCodeBox from './QRCodeBox';
const StickerBox = forwardRef(({ table }, ref) => {
	return (
		<Stack
			direction="column"
			spacing={2}
			ref={ref}
			sx={{
				justifyContent: 'center',
				alignItems: 'center',
				backgroundColor: colors.main.primary300,
				color: colors.text.white,
				borderRadius: '15px',
				padding: '15px',
				width: '200px',
				height: '300px',
			}}>
			<Stack
				direction="row"
				spacing={1}
				sx={{ justifyContent: 'center', alignItems: 'center' }}>
				<Box sx={{ fontWeight: '600', fontSize: '30px' }}>{table.name}</Box>
			</Stack>

			<QRCodeBox url={table.qrLink} />
			<Box sx={{ fontWeight: '600', fontSize: '25px' }}>
				QR코드로 주문하기
			</Box>
		</Stack>
	);
});

export default StickerBox;
