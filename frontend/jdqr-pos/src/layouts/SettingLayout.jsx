import React, { useState, useRef, useEffect } from 'react';
import { useLocation, useNavigate, Outlet } from 'react-router-dom';
import { Button, Box, Typography } from '@mui/material';
import Stack from '@mui/material/Stack';
import { colors } from 'constants/colors';
import AppLogo from 'assets/images/AppLogo.png';
import { strings } from 'constants/strings';
import DefaultLayout from './DefaultLayout';
import RestaurantLogoSample from 'assets/images/RestaurantLogoSample.png';
const SettingLayout = () => {
	const location = useLocation();
	const navigate = useNavigate();
	const isHomePage = location.pathname === '/'; // HomePage 여부 확인

	const headerRef = useRef(null); // 헤더 요소를 참조하기 위한 ref
	const [headerHeight, setHeaderHeight] = useState(0); // 헤더 높이 저장

	// 헤더 높이를 계산하여 상태에 설정
	useEffect(() => {
		if (headerRef.current) {
			setHeaderHeight(headerRef.current.offsetHeight);
		}
	}, []);
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
		<DefaultLayout
			direction="row"
			sx={{
				width: '100%',
				height: '100%',
				background: 'rgba(0,0,0,0)',
			}}>
			{/* 헤더 */}

			<Stack
				direction="row"
				sx={{
					height: '60px',
					position: 'fixed',
					top: 0,
					left: 0,
					width: '100%',
					padding: '10px 40px',
					alignItems: 'center',
					justifyContent: 'space-between',
					zIndex: 1000,
					boxSizing: 'border-box',
					background: 'rgba(255,255,255,0.3)',
					backdropFilter: 'blur(3px)',
				}}>
				<Typography variant="h5" sx={{ fontWeight: 'bold' }}>
					{clock}
				</Typography>
				<Stack
					direction="row"
					spacing={1}
					sx={{
						alignItems: 'center',
						justifyContent: 'center',
					}}>
					<Box sx={{ width: 'auto', height: '40px' }}>
						<img
							style={{
								borderRadius: '10px',
								width: '100%',
								height: '100%',
							}}
							src={RestaurantLogoSample}
							alt={'가게 로고'}
						/>
					</Box>
					<Typography variant="h5" sx={{ fontWeight: 'bold' }}>
						{'바나프레소 강남테헤란로점'}
					</Typography>
				</Stack>
			</Stack>

			<Box
				sx={{
					width: '100%',
					height: '100%',
					minHeight: `calc(100vh - ${headerHeight}px)`,
					paddingTop: `${headerHeight}px`,
					alignItems: 'center',
					justifyContent: 'center',
					background: 'rgba(0,0,0,0)',
					display: 'flex',
				}}>
				<Outlet />
			</Box>
		</DefaultLayout>
	);
};

export default SettingLayout;
