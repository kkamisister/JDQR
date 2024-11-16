import { useState, useEffect } from 'react';
import {
	Dialog,
	Stack,
	Box,
	Typography,
	TextField,
	Grid,
	InputAdornment,
	IconButton,
	darken,
} from '@mui/material';
import { colors } from 'constants/colors';
import DishOptionList from './DishOptionList';
import FlatButton from 'components/button/FlatButton';
import { useQuery } from '@tanstack/react-query';
import { addDishOption, fetchDishOptionList } from 'utils/apis/dish';
import RemoveCircleIcon from '@mui/icons-material/RemoveCircle';
const DishOptionSettingDialog = ({ open, onClose }) => {
	const { isPending, data: dishOptionList } = useQuery({
		queryKey: ['optionList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchDishOptionList(),
	});
	const [selectedOption, setSelectedOption] = useState(null);
	const [isNew, setIsNew] = useState(false);

	const handleAddDishOption = () => {};
	const handleDeleteDishOption = () => {};
	const handleEditDishOption = () => {};

	useEffect(() => {
		console.log(selectedOption);
	}, [selectedOption]);

	return (
		<Dialog maxWidth={'md'} fullWidth={true} onClose={onClose} open={open}>
			<Stack spacing={2} direction="column" sx={{ margin: '20px' }}>
				{/* Dialog 타이틀 */}
				<Box sx={{ fontSize: '40px', fontWeight: 'bold' }}>
					{'옵션 설정'}
				</Box>
				<Grid container sx={{ width: '100%' }}>
					{/* 옵션 목록 */}
					<Grid item xs={5}>
						<Stack direction="column" spacing={2}>
							<Stack
								direction="row"
								sx={{
									justifyContent: 'space-between',
									alignItems: 'flex-end',
								}}>
								<Box sx={{ fontSize: '23px', fontWeight: 'bold' }}>
									옵션 목록
								</Box>
								<Box
									onClick={() => {
										setSelectedOption({
											optionName: '새로운 옵션',
											choices: [],
										});
										setIsNew(true);
									}}
									sx={{
										fontSize: '15px',
										textDecoration: 'underline',
										cursor: 'pointer',
									}}>
									옵션 추가
								</Box>
							</Stack>
							{dishOptionList !== undefined && (
								<DishOptionList
									optionList={dishOptionList.data.options}
									setSelectedOption={setSelectedOption}
									selectedOption={selectedOption}
									setIsNew={setIsNew}
								/>
							)}
						</Stack>
					</Grid>
					<Grid item xs={1}></Grid>
					{/* 각 옵션 설정 */}
					<Grid item xs={6}>
						<Stack direction="column" sx={{ width: '100%' }} spacing={2}>
							<Box>
								<Typography
									variant="subtitle1"
									sx={{ color: colors.text.sub1, fontWeight: 'bold' }}>
									옵션 이름
								</Typography>
								<TextField
									sx={{ width: '80%' }}
									value={
										selectedOption ? selectedOption.optionName : ''
									}
									onChange={e => {
										setSelectedOption(() => {
											return {
												...selectedOption,
												optionName: e.target.value,
											};
										});
									}}
									size="small"
								/>
							</Box>
							<Stack direction="column" spacing={1}>
								<Stack
									direction="row"
									sx={{
										justifyContent: 'space-between',
										alignItems: 'flex-end',

										maxWidth: '80%',
									}}>
									<Typography
										variant="subtitle1"
										sx={{
											color: colors.text.sub1,
											fontWeight: 'bold',
										}}>
										내부 옵션 정보
									</Typography>
									<Box
										onClick={() => {
											setSelectedOption(() => {
												return {
													...selectedOption,
													choices: [
														...selectedOption.choices,
														{
															optionName: '새로운 옵션',
															choices: [],
														},
													],
												};
											});
											setIsNew(true);
										}}
										sx={{
											fontSize: '15px',
											textDecoration: 'underline',
											cursor: 'pointer',
										}}>
										추가
									</Box>
								</Stack>

								{selectedOption &&
									selectedOption.choices.map(choice => (
										<Stack
											direction="row"
											spacing={1}
											sx={{
												justifyContent: 'flex-start',
												alignItems: 'center',
											}}
											key={`option-choice-edit-box-${choice.choiceId}`}>
											<TextField
												label="선택명"
												sx={{ width: '40%' }}
												size="small"
												value={choice.choiceName}
											/>
											<TextField
												label="금액"
												sx={{ width: '30%' }}
												size="small"
												value={choice.price}
												slotProps={{
													input: {
														endAdornment: (
															<InputAdornment position="end">
																원
															</InputAdornment>
														),
													},
												}}
											/>
											<RemoveCircleIcon
												onClick={() => {
													if (choice.optionId === undefined) {
														// TODO: 서버쪽 API가 완성되어야 작성 가능
													}
												}}
												sx={{
													color: colors.point.red,
													'&:hover': {
														color: darken(colors.point.red, 0.2),
													},
													transition: '0.3s',
													cursor: 'pointer',
												}}
											/>
										</Stack>
									))}
							</Stack>
							{isNew && (
								<Stack
									direction="row"
									spacing={1}
									sx={{
										width: '80%',
										justifyContent: 'flex-end',
										alignItems: 'center',
									}}>
									<FlatButton
										text="옵션 등록"
										color={colors.point.blue}
										onClick={handleAddDishOption}
									/>
								</Stack>
							)}
							{!isNew && (
								<Stack
									direction="row"
									spacing={1}
									sx={{
										width: '80%',
										justifyContent: 'flex-end',
										alignItems: 'center',
									}}>
									<FlatButton
										text="옵션 삭제"
										color={colors.point.red}
										onClick={handleDeleteDishOption}
									/>
									<FlatButton
										text="옵션 수정"
										color={colors.point.blue}
										onClick={handleEditDishOption}
									/>
								</Stack>
							)}
						</Stack>
					</Grid>
				</Grid>
			</Stack>
		</Dialog>
	);
};

export default DishOptionSettingDialog;
