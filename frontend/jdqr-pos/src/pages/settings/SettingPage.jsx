import React from 'react';
import { Stack, Typography, Box, Button } from '@mui/material';
import DefaultLayout from 'layouts/DefaultLayout';
import CallSettingIcon from 'assets/images/menu/CallSettingIcon.png';
import DishSettingIcon from 'assets/images/menu/DishSettingIcon.png';
import TableSettingIcon from 'assets/images/menu/TableSettingIcon.png';
import RestaurantSettingIcon from 'assets/images/menu/RestaurantSettingIcon.png';
import BackBreadcrum from 'components/breadcrum/BackBreadcrum';
import SettingCardButton from 'components/card/SettingCardButton';
const SettingPage = () => {
	return (
		<DefaultLayout>
			<BackBreadcrum text={'메인으로'} path={'/'} />
			<Stack
				spacing={5}
				direction="column"
				sx={{ justifyContent: 'center', alignItems: 'center' }}>
				<Stack direction="row" spacing={5}>
					<SettingCardButton
						title={'테이블 설정'}
						path={'/setting/table'}
						icon={TableSettingIcon}
					/>

					<SettingCardButton
						title={'음식 메뉴 설정'}
						path={'/setting/dish'}
						icon={DishSettingIcon}
					/>
				</Stack>
				<Stack direction="row" spacing={5}>
					<SettingCardButton
						title={'사업장 설정'}
						path={'/setting/restaurant'}
						icon={RestaurantSettingIcon}
					/>

					<SettingCardButton
						title={'직원 호출 설정'}
						path={'/setting'}
						icon={CallSettingIcon}
					/>
				</Stack>
			</Stack>
		</DefaultLayout>
	);
};

export default SettingPage;
