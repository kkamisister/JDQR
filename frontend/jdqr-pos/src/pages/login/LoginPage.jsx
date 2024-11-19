import { Stack } from '@mui/material';
import LoginBox from './LoginBox';
import { colors } from 'constants/colors';
const LoginPage = () => {
	return (
		<Stack
			sx={{
				justifyContent: 'center',
				alignItems: 'center',
				backgroundColor: colors.background.primary,
				width: '100vw',
				height: '100vh',
			}}>
			<LoginBox />
		</Stack>
	);
};

export default LoginPage;
