import { useState } from 'react';
import { Stack, Typography, Box, Switch, Container } from '@mui/material';
import PageTitleBox from 'components/title/PageTitleBox';
import OrderBox from './order/OrderBox';
import OrderDetailBox from './order/OrderDetailBox';
import FlatButton from 'components/button/FlatButton';
import { colors } from 'constants/colors';
import TableSettingGridBox from './table/TableSettingGridBox';
import TableGridBox from './table/TableGridBox';
import TableEditBox from './table/edit/TableEditBox';
import QRCodeSettingDialog from './qr/QRCodeSettingDialog';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import {
	fetchTableList,
	removeTable,
	addTable,
	editTable,
} from 'utils/apis/table';
import EmptyTableBox from './table/EmptyTableBox';
const TablePage = () => {
	const queryClient = useQueryClient();
	const { isPending, data } = useQuery({
		queryKey: ['tableList'], // keyword를 queryKey에 포함하여 키워드가 변경되면 새로운 요청 실행
		queryFn: () => fetchTableList(),
		refetchInterval: 1000, // 5초 간격으로 데이터 요청
	});

	const rightMenuPreset = {
		ORDER: 'order',
		ADD: 'add',
		EDIT: 'edit',
		EMPTY: 'empty',
	};
	Object.freeze(rightMenuPreset);
	const [selectedTable, setSelectedTable] = useState(null);
	const handleTableClick = table => {
		setRightMenu(rightMenuPreset.ORDER);
		setSelectedTable(table);
	};
	const [tableViewChecked, setTableViewChecked] = useState(false);
	const [newTableInfo, setNewTableInfo] = useState({});
	const handleTableViewCheckedChange = event => {
		setTableViewChecked(event.target.checked);
	};

	const [rightMenu, setRightMenu] = useState(rightMenuPreset.EMPTY);

	const [open, setOpen] = useState(false);

	const handleClickOpen = () => {
		setOpen(true);
	};

	const handleClose = () => {
		setOpen(false);
	};

	const addNewTable = async () => {
		await addTable(newTableInfo);
		queryClient.invalidateQueries('tableList');
	};
	const editCurrentTable = async () => {
		await editTable(newTableInfo);
		queryClient.invalidateQueries('tableList');
	};
	return data ? (
		<Stack
			direction="column"
			sx={{ padding: '25px', width: '100%' }}
			spacing={2}>
			{/* 페이지 상단 메뉴바 */}
			<Stack
				direction="row"
				spacing={2}
				sx={{ justifyContent: 'space-between', width: '100%' }}>
				<Stack
					spacing={1}
					direction="row"
					sx={{ justifyContent: 'center', alignItems: 'center' }}>
					<PageTitleBox title="주문 상태" />
					{/* <Typography sx={{ fontSize: '25px', fontWeight: '600' }}>
						{'테이블로 보기'}
					</Typography>
					<Switch
						checked={tableViewChecked}
						onChange={handleTableViewCheckedChange}
						sx={{ transform: 'scale(1.5)' }}
					/> */}
				</Stack>
				<FlatButton
					text="테이블 추가"
					fontColor={colors.main.primary700}
					color={colors.main.primary100}
					onClick={() => {
						setRightMenu(rightMenuPreset.ADD);
					}}
					sx={{ marginLeft: 'auto' }} // 오른쪽으로 치우침
				/>
			</Stack>
			<Stack
				direction="row"
				spacing={3}
				sx={{ width: '100%', height: '100%' }}>
				{/* 테이블 정보 리스트 */}
				{!tableViewChecked && (
					<Stack
						direction="row"
						spacing={2}
						useFlexGap
						sx={{
							justifyContent: 'flex-start',
							alignItems: 'flex-start',
							width: '100%', // 원하는 너비로 설정
							flexWrap: 'wrap',
							height: 'fit-content',
						}}>
						{data.data.tables.map(table => (
							<Box
								key={table.name}
								onClick={() => {
									handleTableClick(table);
								}}>
								<OrderBox
									tableName={`${table.name} (${table.people}인)`}
									order={table.dishes}
									color={table.color}
								/>
							</Box>
						))}
					</Stack>
				)}
				{tableViewChecked && (
					<TableSettingGridBox tables={data.data.tables} />
				)}
				{/* 테이블 상세 주문 정보 */}
				<Stack
					spacing={1}
					sx={{ height: '100%', justifyContent: 'space-between' }}>
					{rightMenu === rightMenuPreset.EMPTY && (
						<EmptyTableBox isEdit={false} setTable={setNewTableInfo} />
					)}
					{rightMenu === rightMenuPreset.ORDER && selectedTable && (
						<OrderDetailBox table={selectedTable} />
					)}
					{rightMenu === rightMenuPreset.ADD && (
						<TableEditBox isEdit={false} setTable={setNewTableInfo} />
					)}
					{rightMenu === rightMenuPreset.EDIT && selectedTable && (
						<TableEditBox
							table={selectedTable}
							setTable={setNewTableInfo}
							isEdit={true}
						/>
					)}
					<Stack spacing={1}>
						{selectedTable && (
							<FlatButton
								text="QR 보기"
								onClick={() => {
									handleClickOpen();
								}}
								color={colors.point.blue}
							/>
						)}
						{rightMenu === rightMenuPreset.EDIT && selectedTable && (
							<FlatButton
								text="테이블 저장"
								color={colors.point.red}
								onClick={() => {
									editCurrentTable();
								}}
							/>
						)}

						{rightMenu === rightMenuPreset.ADD && (
							<FlatButton
								text="테이블 저장"
								color={colors.point.red}
								onClick={() => {
									addNewTable();
								}}
							/>
						)}

						{rightMenu === rightMenuPreset.ORDER && selectedTable && (
							<FlatButton
								text="테이블 수정"
								color={colors.point.red}
								onClick={() => {
									setRightMenu(rightMenuPreset.EDIT);
								}}
							/>
						)}
					</Stack>
				</Stack>
			</Stack>

			{selectedTable && (
				<QRCodeSettingDialog
					table={selectedTable}
					open={open}
					onClose={handleClose}
					setTable={setSelectedTable}
				/>
			)}
		</Stack>
	) : (
		<></>
	);
};

export default TablePage;
