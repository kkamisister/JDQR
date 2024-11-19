import { Box } from '@mui/material';
import { colors } from 'constants/colors';
import React from 'react';
import { darken } from '@mui/system';
const TableSelectBox = ({ name, color, row, col }) => {
	console.log(name);
	return (
		<Box
			sx={{
				position: 'relative',
				overflow: 'visible',
				aspectRatio: '1 / 1', // 정사각형 유지
				display: 'flex',
				alignItems: 'center',
				justifyContent: 'center',
				cursor: 'pointer',
				backgroundColor: color === null ? colors.background.primary : color,
				'&:hover': {
					backgroundColor: darken(
						color === null ? colors.background.primary : color,
						0.2
					), // hover 시 색상도 설정 가능
				},
			}}
			onClick={() => console.log(`Clicked cell ${row}, ${col}`)}>
			<Box
				sx={{
					top: '5px',
					left: '5px', // 왼쪽을 고정점으로 설정
					whiteSpace: 'nowrap', // 텍스트 줄바꿈 방지
					overflow: 'visible', // 텍스트가 오른쪽으로 넘치도록 설정
					position: 'absolute',
					zIndex: 1,
				}}>
				{name && `${name}asdasdasdasdasd`}
			</Box>
		</Box>
	);
};

export default TableSelectBox;
