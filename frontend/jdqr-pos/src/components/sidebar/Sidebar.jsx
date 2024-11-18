import { useState } from 'react';
import { Stack, Box, Typography, Switch } from '@mui/material';
import SidebarMenuBox from './SidebarMenuBox';
import { colors } from 'constants/colors';
import { useLocation, useNavigate } from 'react-router-dom';
import {
	fetchBusinessHoursRestaurant,
	changeBusinessHoursRestaurant,
} from 'utils/apis/setting';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { enqueueSnackbar } from 'notistack';
const menuList = [
	{ name: '테이블', path: '/owner/table' },
	{ name: '메뉴', path: '/owner/dish' },
	// { name: '직원 호출', path: '/owner/employee' },
	// { name: '사업장', path: '/owner/restaurant' },
];

const Sidebar = () => {
	const location = useLocation();
	const navigate = useNavigate();
	const queryClient = useQueryClient();
	const handleOfficeHourChange = async () => {
		await changeBusinessHoursRestaurant({ open: !isOfficeHour.data.open });
		queryClient.invalidateQueries('officeHourQuery');
		if (isOfficeHour.data.open) {
			enqueueSnackbar('사업장 영업 종료 완료', { variant: 'success' });
		} else {
			enqueueSnackbar('사업장 영업 시작 완료', { variant: 'success' });
		}
	};
	const { data: isOfficeHour } = useQuery({
		queryKey: ['officeHourQuery'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchBusinessHoursRestaurant(),
	});

	return (
		<Stack
			sx={{
				backgroundColor: colors.background.primary,
				width: 'fit-content',
				justifyContent: 'space-between',
				minHeight: 'calc(100%-40px)',
				padding: '20px',
			}}>
			<Stack spacing={2}>
				{menuList.map(menu => (
					<SidebarMenuBox
						text={menu.name}
						select={location.pathname.includes(menu.path)}
						path={menu.path}
						key={menu.path}
					/>
				))}
				<Stack
					direction="row"
					sx={{
						justifyContent: 'space-around',
						alignItems: 'center',
					}}>
					<Typography sx={{ fontSize: '25px', fontWeight: '600' }}>
						{'영업 여부'}
					</Typography>
					<Switch
						checked={
							isOfficeHour?.data?.open === undefined
								? false
								: isOfficeHour.data.open
						}
						onChange={handleOfficeHourChange}
						sx={{ transform: 'scale(1.5)' }}
					/>
				</Stack>
			</Stack>
		</Stack>
	);
};

export default Sidebar;
