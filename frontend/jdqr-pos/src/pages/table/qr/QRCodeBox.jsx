import ImageBox from 'components/common/ImageBox';
import QRCode from 'qrcode';
import React, { useEffect, useState } from 'react';

const QRCodeBox = ({ url }) => {
	const [qrCodeUrl, setQrCodeUrl] = useState('');

	useEffect(() => {
		const generateQRCode = async () => {
			try {
				const qrCodeDataUrl = await QRCode.toDataURL(url); // URL을 QR 코드로 변환
				setQrCodeUrl(qrCodeDataUrl); // QR 코드 이미지 URL 저장
			} catch (error) {
				console.error('QR Code 생성 오류:', error);
			}
		};

		generateQRCode();
	}, [url]);

	return (
		<ImageBox
			sx={{ width: '100%', height: '100%' }}
			borderRadius={10}
			src={qrCodeUrl}
			alt="QR Code"
		/>
		// {qrCodeUrl ? (

		// ) : (
		// 	<p>QR 코드 생성 중...</p>
		// )}
	);
};

export default QRCodeBox;
