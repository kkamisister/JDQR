import { useRef } from 'react';
import { Dialog, Stack, Box, DialogContent, styled } from '@mui/material';
import StickerBox from './StickerBox';
import FlatButton from 'components/button/FlatButton';
import { colors } from 'constants/colors';
import { enqueueSnackbar } from 'notistack';
import { useReactToPrint } from 'react-to-print';
import domtoimage from 'dom-to-image';
import { saveAs } from 'file-saver';
import { renewTableUrl } from 'utils/apis/table';
import { RepeatOneSharp } from '@mui/icons-material';

const VisuallyHiddenInput = styled('input')({
	clip: 'rect(0 0 0 0)',
	clipPath: 'inset(50%)',
	height: 1,
	overflow: 'hidden',
	position: 'absolute',
	bottom: 0,
	left: 0,
	whiteSpace: 'nowrap',
	width: 1,
});

const handleCopy = async textToCopy => {
	try {
		enqueueSnackbar('복사 완료', { variant: 'success' });

		await navigator.clipboard.writeText(textToCopy); // 텍스트를 클립보드에 복사
	} catch (error) {
		console.error('복사 실패:', error);
		alert('복사에 실패했습니다.');
	}
};
const QRCodeSettingDialog = ({ onClose, open, table, setTable }) => {
	const handleClose = () => {
		onClose();
	};
	const contentRef = useRef(null);
	const reactToPrintFn = useReactToPrint({ contentRef });

	const printQrSticker = () => {
		enqueueSnackbar('프린트 중...', { variant: 'success' });
		reactToPrintFn();
	};
	// 컴포넌트 다운로드 함수
	const onDownloadBtn = () => {
		enqueueSnackbar('이미지 저장 중...', { variant: 'success' });
		domtoimage.toBlob(contentRef.current).then(blob => {
			saveAs(blob, `${table.name}-qr-sticker.png`);
		});
	};

	const handleQrReissue = async () => {
		const response = await renewTableUrl({ tableId: table.tableId });
		console.log(table);
		console.log(response);
		setTable({
			...table,
			qrLink: response.data.data.url,
			qrlastUpdatedAt: response.data.data.qrlastUpdatedAt,
		});
		enqueueSnackbar('QR코드 생성 완료', { variant: 'success' });
	};

	const buttonMenuList = [
		{ text: 'URL 복사', onClick: () => handleCopy(table.qrLink) },
		{
			text: '이미지 파일(.png) 저장',
			onClick: () => onDownloadBtn(),
		},
		{ text: '프린트하기', onClick: () => printQrSticker() },
		{ text: 'QR 코드 재생성', onClick: () => handleQrReissue() },
	];

	return (
		<Dialog maxWidth={'lg'} onClose={handleClose} open={open}>
			<DialogContent>
				<Stack
					direction="row"
					spacing={5}
					sx={{
						padding: '20px',
						justifyContent: 'center',
						alignItems: 'center',
					}}>
					<Stack>
						<StickerBox ref={contentRef} table={table} />
					</Stack>
					<Stack spacing={2}>
						<Box sx={{ fontWeight: '600', fontSize: '40px' }}>
							{table.name}
						</Box>
						<Stack spacing={1}>
							<Stack spacing={0.5}>
								<Box sx={{ fontWeight: '600', fontSize: '25px' }}>
									{'최근 업데이트'}
								</Box>
								<Box>
									{new Date(table.qrlastUpdatedAt).toLocaleString(
										'ko-KR'
									)}
								</Box>
							</Stack>
							<Stack spacing={0.5}>
								<Box sx={{ fontWeight: '600', fontSize: '25px' }}>
									{'URL 주소'}
								</Box>
								<Box sx={{ maxWidth: '400px', wordBreak: 'break-all' }}>
									<a href={table.qrLink}>{table.qrLink}</a>
								</Box>
							</Stack>
						</Stack>
						<Stack direction="column" spacing={1}>
							{buttonMenuList.map(btnInfo => (
								<FlatButton
									key={`qr-btn-${btnInfo.text}`}
									text={btnInfo.text}
									color={colors.point.blue}
									fontColor={colors.text.white}
									onClick={btnInfo.onClick}
								/>
							))}
						</Stack>
					</Stack>
				</Stack>
			</DialogContent>
		</Dialog>
	);
};

export default QRCodeSettingDialog;
