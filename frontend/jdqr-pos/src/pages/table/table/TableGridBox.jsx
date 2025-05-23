import { useState, useEffect } from 'react';
import { Stack, Box } from '@mui/material';
import { colors } from 'constants/colors';
import TableSelectBox from 'components/table/TableSelectBox';

const nullItem = { name: null, color: null, idx: 1 };
const TableGridBox = ({ tables }) => {
	const [tableMap, setTableMap] = useState(
		Array.from({ length: 12 }, () => Array(16).fill(nullItem))
	);

	// 입력받은 테이블 정보를 기반으로 tableMap에 컬러 및 Name 셋팅
	useEffect(() => {
		console.log(tables);
		const initTableMap = Array.from({ length: 12 }, () =>
			Array(16).fill(nullItem)
		);
		tables.forEach((table, idx) => {
			table.position.grid = table.position.grid.sort((a, b) => {
				if (a.x === b.x) {
					return a.y - b.y; // x 값이 같다면 y 값을 기준으로 오름차순 정렬
				}
				return a.x - b.x; // x 값을 기준으로 오름차순 정렬
			});

			table.position.grid.forEach(coord => {
				// console.log(coord);
				// console.log(table.color);
				initTableMap[coord.y][coord.x] = {
					color: table.color,
					idx: idx,
				};
			});
			const startCoord = table.position.grid[0];
			initTableMap[startCoord.y][startCoord.x].name = table.name;
		});
		setTableMap(() => initTableMap);
	}, [tables]);

	return (
		<Stack
			sx={{
				backgroundColor: colors.background.primary,
				padding: '10px',
				borderRadius: '10px',
				justifyContent: 'center',
				alignItems: 'center',
				width: '100%',
			}}>
			<Box
				sx={{
					display: 'grid',
					borderRadius: '20px',
					overflow: 'hidden',
					gridTemplateColumns: 'repeat(16, 1fr)', // 16개의 가로 칸
					width: '100%', // 부모 너비에 맞춤
					height: 'fit-content',
					border: `2px solid ${colors.text.sub2}`,
					backgroundColor: colors.text.sub2,
				}}>
				{tableMap.map((row, rowIdx) =>
					row.map((item, colIdx) => (
						<TableSelectBox
							idx={item.idx}
							name={item.name}
							// id={item.id}
							key={`grid-${16 * rowIdx + colIdx + 1}`}
							row={rowIdx}
							col={colIdx}
							color={item.color}
						/>
					))
				)}
			</Box>
		</Stack>
	);
};

export default TableGridBox;
