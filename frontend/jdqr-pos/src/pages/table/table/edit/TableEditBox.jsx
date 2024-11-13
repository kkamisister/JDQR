import { useState, useEffect } from 'react';
import { Box, Stack, TextField, Typography } from '@mui/material';
import { colors } from 'constants/colors';
import SubtitleTextField from 'components/input/SubtitleTextField';
import EditColorBox from './EditColorBox';
import FlatButton from 'components/button/FlatButton';
import { removeTable } from 'utils/apis/table';
import { useQueryClient } from '@tanstack/react-query';
const TableEditBox = ({
	table = {
		name: '새로운 테이블',
		color: colors.table[0],
		people: 0,
	},
	isEdit,
	setTable,
}) => {
	const queryClient = useQueryClient();
	const [tableColor, setTableColor] = useState(table.color);
	const [tableName, setTableName] = useState(table.name);
	const [tablePeople, setTablePeople] = useState(table.people);

	useEffect(() => {
		if (isEdit) {
			setTable({
				tableId: table.tableId,
				name: tableName,
				color: tableColor,
				people: tablePeople,
			});
		} else {
			setTable({
				name: tableName,
				color: tableColor,
				people: tablePeople,
			});
		}
	}, [table, isEdit, setTable, tableColor, tableName, tablePeople]);

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
				<SubtitleTextField
					title="테이블 이름"
					value={tableName}
					setValue={setTableName}
				/>
				<EditColorBox
					currentColorCode={tableColor}
					setTableColor={setTableColor}
				/>
				<SubtitleTextField
					title="최대 인원 수"
					value={tablePeople}
					setValue={setTablePeople}
				/>
			</Stack>
			{isEdit && (
				<FlatButton
					text={'테이블 삭제'}
					color={colors.main.primary300}
					onClick={async () => {
						await removeTable({ tableId: table.tableId });
						queryClient.invalidateQueries('tableList');
					}}
				/>
			)}
		</Stack>
	);
};

export default TableEditBox;
