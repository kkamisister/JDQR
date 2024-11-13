import React from 'react';
import { Dialog, Stack, Box } from '@mui/material';
const DishCategorySettingDialog = ({ open, onClose }) => {
	return (
		<Dialog onClose={onClose} open={open}>
			<Stack></Stack>
			<Box></Box>
		</Dialog>
	);
};

export default DishCategorySettingDialog;
