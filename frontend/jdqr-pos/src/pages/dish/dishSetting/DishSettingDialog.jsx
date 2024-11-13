import React, { useState } from 'react';
import { Dialog, Stack, Box, Button, ButtonGroup } from '@mui/material';
import FlatButton from 'components/button/FlatButton';
import { colors } from 'constants/colors';
import SubtitleTextField from 'components/textfield/SubtitleTextField';
import ImageBox from 'components/common/ImageBox';
const DishSettingDialog = ({ open, onClose, isEdit = false }) => {
	const [name, setName] = useState('새로운 상품명');
	const [description, setDescription] = useState('새로운 상품 설명');
	const [price, setPrice] = useState(10000);
	const [categoryList, setCategoryList] = useState([]);
	const [tagList, setTagList] = useState([]);

	return (
		<Dialog onClose={onClose} open={open}>
			<Stack direction="column" sx={{ padding: '20px' }}>
				{/* Dialog 타이틀 */}
				<Box sx={{ fontSize: '40px', fontWeight: 'bold' }}>
					{isEdit && '상품 정보 수정'}
					{!isEdit && '상품 정보 추가'}
				</Box>
				{/* 내부 Content */}
				<Stack direction="row">
					{/* 사진 추가, 저장 및 삭제 버튼 */}
					<Stack direction="column">
						<Stack direction="row">
							<FlatButton text="상품 저장" color={colors.point.blue} />
							{isEdit && (
								<FlatButton text="상품 삭제" color={colors.point.red} />
							)}
						</Stack>
					</Stack>
					<Stack direction="column">
						<ButtonGroup aria-label="상품 정보 수정 옵션 버튼 그룹">
							<Button>{'상품 수정'}</Button>
							<Button>{'옵션'}</Button>
						</ButtonGroup>

						<SubtitleTextField
							title="상품명"
							value={name}
							setValue={setName}
						/>
						<SubtitleTextField
							title="카테고리"
							value={categoryList}
							setValue={setCategoryList}
						/>
						<SubtitleTextField
							title="가격(원)"
							value={price}
							setValue={setPrice}
						/>

						<SubtitleTextField
							title="상품 설명"
							value={description}
							setValue={setDescription}
						/>

						<SubtitleTextField
							title="태그"
							value={tagList}
							setValue={setTagList}
						/>
					</Stack>
				</Stack>
			</Stack>
		</Dialog>
	);
};

export default DishSettingDialog;
