import { useState } from 'react';
import { Box, Stack, Button, TextField, Typography } from '@mui/material';
import AppLogo from 'assets/images/AppLogo.png';
import MenuCard from 'components/card/MenuCard';
import DefaultLayout from 'layouts/DefaultLayout';
const LoginPage = () => {
	const [userCode, setUserCode] = useState('');

	return (
		<DefaultLayout>
			<MenuCard
				direction="column"
				sx={{
					width: '400px',
					height: '300px',
					display: 'flex',
					alignSelf: 'center',
					textAlign: 'center',
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
						src={AppLogo}
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
			</MenuCard>
		</DefaultLayout>
	);
};

export default LoginPage;
