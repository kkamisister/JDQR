import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import { colors } from 'constants/colors';
import DishList from './DishList';

function CustomTabPanel(props) {
	const { children, value, index, ...other } = props;

	return (
		<div
			role="tabpanel"
			hidden={value !== index}
			id={`category-tabpanel-${index}`}
			aria-labelledby={`category-tab-${index}`}
			{...other}>
			{value === index && <Box sx={{ paddingTop: '20px' }}>{children}</Box>}
		</div>
	);
}

CustomTabPanel.propTypes = {
	children: PropTypes.node,
	index: PropTypes.number.isRequired,
	value: PropTypes.number.isRequired,
};

function a11yProps(index) {
	return {
		id: `category-tab-${index}`,
		'aria-controls': `category-tabpanel-${index}`,
	};
}
const DishPage = () => {
	const [value, setValue] = React.useState(0);

	const handleChange = (event, newValue) => {
		setValue(newValue);
	};

	return (
		<Box sx={{ width: '100%', padding: '20px' }}>
			<Box sx={{ borderBottom: 1, borderColor: colors.main.primary100 }}>
				<Tabs
					value={value}
					onChange={handleChange}
					aria-label="basic tabs example"
					TabIndicatorProps={{
						style: {
							backgroundColor: colors.main.primary500, // 인디케이터 색상 설정
						},
					}}>
					<Tab
						sx={{
							color: `${colors.text.main} !important`,
							fontWeight: value === 0 ? 'bold' : 'normal',
							fontSize: '30px',
						}}
						label="탕류"
						{...a11yProps(0)}
					/>
					<Tab
						sx={{
							color: `${colors.text.main} !important`,
							fontWeight: value === 1 ? 'bold' : 'normal',
							fontSize: '30px',
						}}
						label="사이드"
						{...a11yProps(1)}
					/>
					<Tab
						sx={{
							color: `${colors.text.main} !important`,
							fontWeight: value === 2 ? 'bold' : 'normal',
							fontSize: '30px',
						}}
						label="음료"
						{...a11yProps(2)}
					/>
				</Tabs>
			</Box>
			<CustomTabPanel value={value} index={0}>
				<DishList />
			</CustomTabPanel>
			<CustomTabPanel value={value} index={1}>
				<DishList />
			</CustomTabPanel>
			<CustomTabPanel value={value} index={2}>
				<DishList />
			</CustomTabPanel>
		</Box>
	);
};

export default DishPage;
