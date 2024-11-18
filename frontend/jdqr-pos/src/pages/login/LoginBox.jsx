import { useState } from 'react';
import {
	Button,
	Grid,
	Stack,
	TextField,
	Typography,
	Divider,
} from '@mui/material';
import ImageBox from 'components/common/ImageBox';
import RestaurantLogoSample from 'assets/images/RestaurantLogoSample.png';
import { colors } from 'constants/colors';
import { useNavigate } from 'react-router-dom';
import { enqueueSnackbar } from 'notistack';
import { fetchLoginInfoByCode } from 'utils/apis/auth';
import { fetchRestaurant } from 'utils/apis/setting';

const LoginBox = () => {
	const navigate = useNavigate();
	const [code, setCode] = useState('');
	const handleLoginButtonClicked = async () => {
		if (code.trim() === '') {
			enqueueSnackbar('유효하지 않은 코드 형식입니다', {
				variant: 'warning',
			});
			return;
		}

		await fetchLoginInfoByCode(code);
		console.log(sessionStorage.getItem('accessToken'));
		if (sessionStorage.getItem('accessToken')) {
			navigate('/owner/table');
		} else {
			enqueueSnackbar('유효하지 않은 코드입니다', {
				variant: 'error',
			});
			return;
		}
	};
	const handleEnterKeyPress = event => {
		if (event.key === 'Enter') {
			event.preventDefault(); // 기본 동작 방지 (필요한 경우)
			handleLoginButtonClicked();
		}
	};
	return (
		<Grid
			container
			direction="row"
			sx={{
				padding: '20px',
				width: '600px',
				height: '400px',
				justifyContent: 'center',
				alignItems: 'center',
				borderRadius: '20px',
				backgroundColor: colors.background.white,
			}}>
			<Grid item xs={5.9}>
				<Stack
					direction="column"
					spacing={2}
					sx={{
						justifyContent: 'center',
						alignItems: 'center',
						padding: '20px',
					}}>
					<ImageBox
						src={RestaurantLogoSample}
						alt="JDQR 로고"
						borderRadius="10px"
						sx={{
							width: '130px',
							height: '130px',
							justifySelf: 'center',
						}}
					/>
					<Stack>
						<Typography
							variant="h4"
							sx={{ color: colors.text.main, fontWeight: 'bold' }}>
							{'JDQR'}
						</Typography>
						<Typography
							variant="subtitle1"
							sx={{ color: colors.text.main }}>
							{'QR로 쓰는 테이블오더 서비스'}
						</Typography>
					</Stack>
				</Stack>
			</Grid>
			<Divider orientation="vertical" flexItem />
			<Grid item xs={5.9}>
				<Stack
					direction="column"
					sx={{
						padding: '20px',
					}}>
					<Typography
						variant="subtitle1"
						sx={{ color: colors.text.main, fontWeight: 'bold' }}>
						{'사업장 코드'}
					</Typography>
					<Stack spacing={1}>
						<TextField
							size="small"
							placeholder="사업장 코드 입력"
							onKeyDown={handleEnterKeyPress}
							onChange={e => {
								setCode(e.target.value);
							}}
							value={code}
						/>
						<Button
							variant="contained"
							disableElevation
							size="small"
							sx={{
								fontWeight: 'bold',
								backgroundColor: colors.main.primary500,
							}}
							onClick={handleLoginButtonClicked}>
							로그인
						</Button>
					</Stack>
				</Stack>
			</Grid>
		</Grid>
	);
};

export default LoginBox;
