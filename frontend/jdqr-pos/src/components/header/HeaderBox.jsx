import { Stack, Typography } from '@mui/material';
import { colors } from 'constants/colors';
import { useRef, useState, useEffect } from 'react';
import RestaurantLogoSample from 'assets/images/RestaurantLogoSample.png';
import ImageBox from 'components/common/ImageBox';
const HeaderBox = () => {
	const currentTime = () => {
		const now = new Date();

		const year = now.getFullYear();
		const month = String(now.getMonth() + 1).padStart(2, '0');
		const date = String(now.getDate()).padStart(2, '0');
		const weekdays = ['일', '월', '화', '수', '목', '금', '토'];
		const week = weekdays[now.getDay()];

		const hour = String(now.getHours()).padStart(2, '0');
		const minute = String(now.getMinutes()).padStart(2, '0');
		const second = String(now.getSeconds()).padStart(2, '0');

		return `(${week}) ${year}/${month}/${date} ${hour}:${minute}:${second}`;
	};

	// useState 이용, 랜더링 후 첫 값은 nowTime return 값 사용
	const [clock, setclock] = useState(currentTime);

	// 1초마다 clock의 값을 다시 계산 후 랜더링 (setClock 이용)
	setInterval(() => setclock(currentTime), 1000);

	return (
		<Stack
			direction="row"
			sx={{
				justifyContent: 'space-between',
				alignItems: 'center',
				padding: '10px 20px 10px 20px',
				backgroundColor: colors.background.box,
			}}>
			<Typography variant="h4" sx={{ fontWeight: 'bold' }}>
				{clock}
			</Typography>
			<Stack
				direction="row"
				spacing={1}
				sx={{
					justifyContent: 'space-between',
					alignItems: 'center',
				}}>
				<ImageBox
					src={RestaurantLogoSample}
					alt="JDQR 로고"
					borderRadius="5px"
					sx={{
						width: '55px',
						height: '55px',
						justifySelf: 'center',
					}}
				/>
				<Typography variant="h4" sx={{ fontWeight: 'bold' }}>
					{'바나프레소 강남테헤란로점'}
				</Typography>
			</Stack>
		</Stack>
	);
};

export default HeaderBox;
