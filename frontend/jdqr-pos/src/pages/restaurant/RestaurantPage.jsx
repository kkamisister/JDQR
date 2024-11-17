import React from 'react';
import { Stack, Box } from '@mui/material';
import PageTitleBox from 'components/title/PageTitleBox';
import { colors } from 'constants/colors';
import { fetchRestaurant } from 'utils/apis/setting';
import { useQuery, useQueryClient } from '@tanstack/react-query';

const RestaurantPage = () => {
	return (
		<Stack sx={{ width: '100%', padding: '20px' }} spacing={1}>
			<Stack
				direction="row"
				sx={{
					justifyContent: 'space-between',
				}}>
				<Stack
					spacing={1}
					direction="row"
					sx={{ justifyContent: 'center', alignItems: 'center' }}>
					<PageTitleBox title="사업장 정보" />
				</Stack>
			</Stack>
			<Box
				sx={{
					backgroundColor: colors.background.primary,
					width: 'fit-content',
					padding: '20px',
				}}>
				<Stack>dasdasd</Stack>
			</Box>
		</Stack>
	);
};

export default RestaurantPage;
