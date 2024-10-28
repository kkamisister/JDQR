import { useState } from 'react';
import { Box, Stack, Button, TextField, Typography } from '@mui/material';
import { colors } from 'constants/colors';
import logoImage from 'assets/images/logo.png';
const LoginPage = () => {
	const [userCode, setUserCode] = useState('');

	return (
		<Box
			sx={{
				width: '100%',
				height: '100%',
				display: 'flex',
				justifyContent: 'center',
			}}>
			<Box
				sx={{
					position: 'fixed',
					aspectRatio: '1 / 1',
					top: '-224vh',
					height: '300vh',
					background:
						'radial-gradient(closest-side, #89d8d8 0%, rgba(49,130,246,0.15) 70%, rgba(255,255,255,0) 100%)',
					pointerEvents: 'none',
					zIndex: '-1',
				}}
			/>
			<Stack
				direction="column"
				sx={{
					width: '400px',
					height: '300px',
					borderRadius: '20px',
					backgroundColor: colors.background.white, // 박스 배경색 설정
					display: 'flex',
					alignSelf: 'center',
					textAlign: 'center',
					padding: '20px',
					justifyContent: 'space-evenly',
					alignItems: 'center',
				}}>
				<Box
					sx={{
						width: '100px',
						height: '100px',
						borderRadius: '25%',
						overflow: 'hidden',
					}}>
					<img
						style={{ width: '100%', height: '100%' }}
						src={logoImage}
						alt="로고 이미지"
					/>
				</Box>
				<Typography sx={{ fontSize: '24px', fontWeight: '600' }}>
					사업장 로그인
				</Typography>
				<TextField
					fullWidth
					placeholder="사업장 코드 입력"
					id="fullWidth"
					size="small"
					value={userCode}
					onChange={e => setUserCode(e.target.value)} // 키워드 변경 시 state 업데이트
				/>
				<Button variant="contained" disableElevation sx={{ width: '100%' }}>
					로그인
				</Button>
			</Stack>
		</Box>
	);
};

export default LoginPage;
