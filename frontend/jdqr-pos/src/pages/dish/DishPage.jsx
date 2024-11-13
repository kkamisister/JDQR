import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Box, Stack } from '@mui/material';
import { colors } from 'constants/colors';
import DishList from './DishList';
import { dishData } from 'sampleData/apiMock';
import FlatButton from 'components/button/FlatButton';
import DishSettingDialog from './dishSetting/DishSettingDialog';
import DishCategorySettingDialog from './dishCategorySetting/DishCategorySettingDialog';
import DishOptionSettingDialog from './dishOptionSetting/DishOptionSettingDialog';
import { useQueryClient, useQuery } from '@tanstack/react-query';
import { fetchDishList } from 'utils/apis/dish';
import PageTitleBox from 'components/title/PageTitleBox';
const dishes = dishData.data.dishes;

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
	const queryClient = useQueryClient();
	const { isPending, data: dishList } = useQuery({
		queryKey: ['dishList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchDishList(),
	});

	/**
	 * 카테고리 탭 관련 상태 처리
	 */
	const [categoryList, setCategoryList] = useState([]);
	const [categoryTabValue, setCategoryTabValue] = useState(0);
	const handleCategoryTabValueChange = (event, newValue) => {
		setCategoryTabValue(newValue);
	};

	/**
	 * 카테고리 설정 Dialog 관련 상태 처리
	 */
	const [dishCategoryDialogOpen, setDishCategoryDialogOpen] = useState(false);
	const handleDishCategoryDialogOpen = () => {
		setDishCategoryDialogOpen(true);
	};

	const handleDishCategoryDialogClose = () => {
		setDishCategoryDialogOpen(false);
	};

	/**
	 * 메뉴 설정 Dialog 관련 상태 처리
	 */
	const [dishDialogOpen, setDishDialogOpen] = useState(false);
	const handleDishDialogOpen = () => {
		setDishDialogOpen(true);
	};

	const handleDishDialogClose = () => {
		setDishDialogOpen(false);
	};

	/**
	 * 옵션 설정 Dialog 관련 상태 처리
	 */
	const [dishOptionDialogOpen, setDishOptionDialogOpen] = useState(false);
	const handleDishOptionDialogOpen = () => {
		setDishOptionDialogOpen(true);
	};

	const handleDishOptionDialogClose = () => {
		setDishOptionDialogOpen(false);
	};

	useEffect(() => {
		console.log(dishList);
	}, [dishList]);

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
					<PageTitleBox title="메뉴 설정" />
				</Stack>

				<Stack direction="row" spacing={1}>
					<FlatButton
						text="메뉴 추가"
						color={colors.main.primary300}
						onClick={handleDishDialogOpen}
					/>
					<FlatButton
						text="옵션 설정"
						color={colors.main.primary300}
						onClick={handleDishOptionDialogOpen}
					/>
					<FlatButton
						text="카테고리 설정"
						color={colors.main.primary300}
						onClick={handleDishCategoryDialogOpen}
					/>
				</Stack>
			</Stack>
			<Stack>
				<Tabs
					value={categoryTabValue}
					onChange={handleCategoryTabValueChange}
					aria-label="음식 카테고리 탭"
					variant="scrollable"
					scrollButtons="auto"
					TabIndicatorProps={{
						style: {
							backgroundColor: colors.main.primary200, // 인디케이터 색상 설정
						},
					}}>
					{dishList !== undefined &&
						dishList.data.dishCategories.map((category, index) => (
							<Tab
								sx={{
									color: `${colors.text.main} !important`,
									fontWeight:
										categoryTabValue === index ? 'bold' : 'normal',
									fontSize: '30px',
								}}
								label={category}
								{...a11yProps(index)}
							/>
						))}
				</Tabs>
				{dishList !== undefined &&
					dishList.data.dishes.map((category, index) => (
						<CustomTabPanel value={categoryTabValue} index={index}>
							<DishList dishItems={category.items} />
						</CustomTabPanel>
					))}
			</Stack>
			<DishSettingDialog
				open={dishDialogOpen}
				onClose={handleDishDialogClose}
			/>
			<DishCategorySettingDialog
				open={dishCategoryDialogOpen}
				onClose={handleDishCategoryDialogClose}
			/>
			<DishOptionSettingDialog
				open={dishOptionDialogOpen}
				onClose={handleDishOptionDialogClose}
			/>
		</Stack>
	);
};

export default DishPage;
