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
import { useQuery, useQueryClient } from '@tanstack/react-query';
import {
	addDishOption,
	deleteDishOption,
	editDishOption,
	fetchDishOptionList,
} from 'utils/apis/dish';
import RemoveCircleIcon from '@mui/icons-material/RemoveCircle';
import { enqueueSnackbar } from 'notistack';

const DishOptionSettingDialog = ({ open, onClose }) => {
	const queryClient = useQueryClient();
	const { isPending, data: dishOptionList } = useQuery({
		queryKey: ['optionList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchDishOptionList(),
		onSuccess: data => {
			// 데이터를 가져온 후 state 업데이트
			console.log(data);
			if (data?.options.length > 0) {
				setSelectedOption(data?.options[0]); // 첫 번째 옵션을 기본값으로 설정
			}
		},
	});
	const [selectedOption, setSelectedOption] = useState(null);
	const [isNew, setIsNew] = useState(false);

	const handleAddDishOption = async () => {
		await addDishOption(selectedOption);
		enqueueSnackbar('옵션을 추가했어요', { variant: 'success' });
		queryClient.invalidateQueries('optionList');
	};
	const handleDeleteDishOption = async () => {
		await deleteDishOption({ optionId: selectedOption.optionId });
		enqueueSnackbar('옵션을 삭제했어요', { variant: 'error' });
		queryClient.invalidateQueries('optionList');
	};
	const handleEditDishOption = async () => {
		console.log(selectedOption);
		await editDishOption(selectedOption);
		enqueueSnackbar('옵션을 수정했어요', { variant: 'warning' });
		queryClient.invalidateQueries('optionList');
	};

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
															choiceId: null,
															choiceName: '새로운 옵션',
															choices: [],
														},
													],
												};
											});
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
									selectedOption.choices.map((choice, idx) => (
										<Stack
											direction="row"
											spacing={1}
											sx={{
												justifyContent: 'flex-start',
												alignItems: 'center',
											}}
											key={`option-choice-edit-box-${idx}`}>
											<TextField
												label="선택명"
												sx={{ width: '40%' }}
												size="small"
												value={choice.choiceName}
												onChange={e => {
													setSelectedOption(prev => {
														return {
															...prev,
															choices: prev.choices.map(
																_choice => {
																	if (_choice === choice) {
																		_choice.choiceName =
																			e.target.value;
																	}
																	return _choice;
																}
															),
														};
													});
												}}
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
												onChange={e => {
													setSelectedOption(prev => {
														return {
															...prev,
															choices: prev.choices.map(
																_choice => {
																	if (_choice === choice) {
																		_choice.price =
																			e.target.value;
																	}
																	return _choice;
																}
															),
														};
													});
												}}
											/>
											<RemoveCircleIcon
												onClick={async () => {
													// 서버에는 없고 로컬에서만 존재하는 choice
													if (choice.choiceId === null) {
														setSelectedOption(prev => {
															return {
																...prev,
																choices: prev.choices.filter(
																	_choice => _choice !== choice
																),
															};
														});
													}
													// 서버에 있는 choice
													else {
														setSelectedOption(prev => {
															return {
																...prev,
																choices: prev.choices.filter(
																	_choice =>
																		_choice.choiceId !==
																		choice.choiceId
																),
															};
														});
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
