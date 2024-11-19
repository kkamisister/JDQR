import { useState } from 'react';
import { Typography, Stack } from '@mui/material';
import { colors } from 'constants/colors';
import { darken } from '@mui/system';
import DishAddDialog from './dishSetting/DishAddDialog';

const DishListItem = ({ name, price, tags, onClick }) => {
	return (
		<Stack
			spacing={0.75}
			sx={{
				fontSize: '20px',
				color: colors.text.main,
				backgroundColor: colors.background.primary,
				width: '280px',
				height: '100%',
				padding: '20px',
				borderRadius: '10px',
				'&:hover': {
					backgroundColor: darken(colors.background.primary, 0.2), // hover 시 색상도 설정 가능
				},
				transition: 'all 0.3s',
				cursor: 'pointer',
			}}
			onClick={onClick}>
			<Stack>
				<Typography sx={{ fontSize: '22px', fontWeight: '600' }}>
					{name}
				</Typography>
				<Typography sx={{ fontSize: '22px', fontWeight: '600' }}>
					{`${price.toLocaleString('ko-KR')}원`}
				</Typography>
			</Stack>
			{/* <Stack direction="row" spacing={1}>
				{tags.map(label => (
					<Chip
						sx={{
							fontSize: '16px',
							fontWeight: '600',
							bgcolor: colors.table[0],
							color: colors.text.white,
						}}
						label={label}
					/>
				))}
			</Stack> */}
		</Stack>
	);
};

export default DishListItem;
