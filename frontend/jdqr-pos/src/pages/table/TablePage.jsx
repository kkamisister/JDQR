import { useState } from 'react';
import { Stack, Typography, Box, Switch } from '@mui/material';
import PageTitleBox from 'components/title/PageTitleBox';
import OrderBox from './order/OrderBox';
import { tableData } from 'sampleData/apiMock';
import OrderDetailBox from './order/OrderDetailBox';
import FlatButton from 'components/button/FlatButton';
import { colors } from 'constants/colors';
const SampleTableRequestData = tableData;
const TablePage = () => {
	const [selectedTable, setSelectedTable] = useState(
		SampleTableRequestData.data.tables[0]
	);
	console.log(SampleTableRequestData.data.tables);
	const handleTableClick = table => {
		setSelectedTable(table);
	};
	return (
		<Stack direction="column" sx={{ padding: '25px' }} spacing={2}>
			{/* 페이지 상단 메뉴바 */}
			<Stack direction="row" spacing={2}>
				<PageTitleBox title="주문 상태" />
				<Stack
					spacing={0.5}
					direction="row"
					sx={{ justifyContent: 'center', alignItems: 'center' }}>
					<Typography sx={{ fontSize: '25px', fontWeight: '600' }}>
						{'테이블로 보기'}
					</Typography>
					<Switch sx={{ transform: 'scale(1.5)' }} />
				</Stack>
			</Stack>

			<Stack direction="row" spacing={3} sx={{ height: '100%' }}>
				{/* 테이블 정보 리스트 */}
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
					{SampleTableRequestData.data.tables.map(table => (
						<Box onClick={() => handleTableClick(table)}>
							<OrderBox
								tableName={`${table.name} (${table.people}인)`}
								order={table.dishes}
								color={table.color}
							/>
						</Box>
					))}
				</Stack>
				{/* 테이블 상세 주문 정보 */}
				<Stack
					spacing={1}
					sx={{ height: '100%', justifyContent: 'space-between' }}>
					<OrderDetailBox table={selectedTable} />
					<Stack spacing={1} sx={{}}>
						<FlatButton text="QR 보기" color={colors.point.blue} />
						<FlatButton text="테이블 삭제" color={colors.point.red} />
					</Stack>
				</Stack>
			</Stack>
		</Stack>
	);
};

export default TablePage;
