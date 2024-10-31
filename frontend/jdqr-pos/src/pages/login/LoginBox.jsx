import React from 'react';
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
const LoginBox = () => {
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
						<TextField size="small" placeholder="사업장 코드 입력" />
						<Button
							variant="contained"
							disableElevation
							size="small"
							sx={{
								fontWeight: 'bold',
								backgroundColor: colors.main.primary500,
							}}>
							로그인
						</Button>
					</Stack>
				</Stack>
			</Grid>
		</Grid>
	);
};

export default LoginBox;
