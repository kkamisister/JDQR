import { useState } from 'react';
import { Box, Stack, TextField, Typography } from '@mui/material';
import { colors } from 'constants/colors';
import SubtitleTextField from 'components/textfield/SubtitleTextField';
import EditColorBox from './EditColorBox';
import FlatButton from 'components/button/FlatButton';
const TableEditBox = ({
	table = {
		name: '새로운 테이블',
		color: colors.table[0],
		people: '최대 인원수',
	},
	isEdit,
}) => {
	const [tableColor, setTableColor] = useState(table.color);
	if (!isEdit) {
	}
	return (
		<Stack
			spacing={1.5}
			sx={{
				borderRadius: '10px',
				width: '350px',
				padding: '20px',
				backgroundColor: colors.background.primary,
				height: '100%',
			}}>
			<Box sx={{ fontSize: '30px', fontWeight: 'bold' }}>
				테이블 {isEdit ? '수정' : '추가'}
			</Box>
			<Stack
				spacing={1}
				sx={{
					height: '100%',
					width: '100%',
				}}>
				<SubtitleTextField title="테이블 이름" placeholder={table.name} />
				<EditColorBox
					currentColorCode={tableColor}
					setTableColor={setTableColor}
				/>
				<SubtitleTextField
					title="최대 인원 수"
					placeholder={table.people}
				/>
			</Stack>
			{isEdit && (
				<FlatButton text={'테이블 삭제'} color={colors.main.primary300} />
			)}
		</Stack>
	);
};

export default TableEditBox;
