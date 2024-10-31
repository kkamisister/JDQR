import React from 'react';
import { Stack } from '@mui/material';
import SidebarMenuBox from './SidebarMenuBox';
import { colors } from 'constants/colors';
import { useLocation, useNavigate } from 'react-router-dom';
const menuList = [
	{ name: '테이블', path: '/table' },
	{ name: '메뉴', path: '/dish' },
	{ name: '직원 호출', path: '/employee' },
	{ name: '사업장', path: '/restaurant' },
];
const Sidebar = () => {
	const location = useLocation();
	const navigate = useNavigate();

	return (
		<Stack
			spacing={2}
			sx={{
				backgroundColor: colors.background.primary,
				width: 'fit-content',
				minHeight: 'calc(100%-40px)',
				padding: '20px',
			}}>
			{menuList.map(menu => (
				<SidebarMenuBox
					text={menu.name}
					select={location.pathname.includes(menu.path)}
					path={menu.path}
					key={menu.path}
				/>
			))}
		</Stack>
	);
};

export default Sidebar;
