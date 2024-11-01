import React from 'react';
import { Box, Divider, Stack } from '@mui/material';
import { colors } from 'constants/colors';

const PageTitleBox = ({ title }) => {
	return (
		<Box>
			<Stack spacing={1}>
				<Box
					sx={{
						fontSize: '35px',
						fontWeight: 'bold',
						color: colors.text.main,
						paddingLeft: '15px',
						paddingRight: '15px',
					}}>
					{title}
				</Box>
				<Divider
					sx={{
						borderColor: colors.main.primary500,
						borderBottomWidth: 4,
					}}
				/>
			</Stack>
		</Box>
	);
};

export default PageTitleBox;
