import React from 'react';
import { Stack, Typography, Box, Button } from '@mui/material';

import DefaultLayout from 'layouts/DefaultLayout';
import MenuCardButton from 'components/card/MenuCardButton';
import OrderStatusIcon from 'assets/images/menu/OrderStatusIcon.png';
import SettingIcon from 'assets/images/menu/SettingIcon.png';
import RestaurantLogoSample from 'assets/images/RestaurantLogoSample.png';
import { useNavigate } from 'react-router-dom';
import SettingCardButton from 'components/card/SettingCardButton';

const HomePage = () => {
	const navigate = useNavigate();

	return (
		<DefaultLayout>
			<Stack
				spacing={5}
				direction="column"
				sx={{ justifyContent: 'center', alignItems: 'center' }}>
				<Stack
					direction="row"
					spacing={3}
					sx={{ justifyContent: 'center', alignItems: 'center' }}>
					<Box sx={{ width: 'auto', height: '100px' }}>
						<img
							style={{
								borderRadius: '20px',
								width: '100%',
								height: '100%',
							}}
							src={RestaurantLogoSample}
							alt={'가게 로고'}
						/>
					</Box>
					<Typography variant="h4">
						<span style={{ fontWeight: 'bold' }}>
							{'바나프레소 테헤란로'}
						</span>
						&nbsp;
						{'점주님!'}
						<br />
						{'안녕하세요!'}
					</Typography>
				</Stack>
				<Stack direction="row" spacing={5}>
					<MenuCardButton
						onClick={() => {
							navigate(`/order`);
						}}>
						<Stack
							direction="row"
							spacing={3}
							sx={{
								justifyContent: 'center',
								padding: '40px',
								alignItems: 'center',
							}}>
							<Box sx={{ width: '100px', height: '100px' }}>
								<img
									style={{ width: '100%', height: '100%' }}
									src={OrderStatusIcon}
									alt={'주문 상황 보기'}
								/>
							</Box>
							<Typography variant="h4" sx={{ fontWeight: 'bold' }}>
								주문 상황 보기
							</Typography>
						</Stack>
					</MenuCardButton>
					<SettingCardButton
						title={'설정'}
						path={'/setting'}
						icon={SettingIcon}
					/>
				</Stack>
			</Stack>
		</DefaultLayout>
	);
};

export default HomePage;
