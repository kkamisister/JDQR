import React from 'react';
import { Dialog, Stack, Box } from '@mui/material';
const DishOptionSettingDialog = ({ open, onClose }) => {
	return (
		<Dialog maxWidth={'md'} fullWidth={true} onClose={onClose} open={open}>
			<Stack spacing={2} direction="column" sx={{ margin: '20px' }}>
				{/* Dialog 타이틀 */}
				<Box sx={{ fontSize: '40px', fontWeight: 'bold' }}>
					{'옵션 설정'}
				</Box>
			</Stack>
		</Dialog>
	);
};

export default DishOptionSettingDialog;
