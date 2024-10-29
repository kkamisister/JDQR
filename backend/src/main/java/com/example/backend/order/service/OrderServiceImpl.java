package com.example.backend.order.service;

import org.springframework.stereotype.Service;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final TableRepository tableRepository;
	private final GenerateLink generateLink;
	/**
	 * tableName으로 qrCode를 찾아서, 해당 코드에 token을 더한 주소를 반환
	 * @param tableId
	 * @return
	 */
	@Override
	public String redirectUrl(String tableId,String uuid) {
		//1. table을 찾는다
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		log.warn("table : {}",table);

		// 현재 url이 유효하지 않다면 예외를 반환한다
		String targetUrl = GenerateLink.AUTH_PREFIX + "/"+tableId+"/"+uuid;
		if(!targetUrl.equals(table.getQrCode())){
			throw new JDQRException(ErrorCode.FUCKED_UP_QR);
		}

		//2. table의 링크에 token을 생성한다
		String authLink = generateLink.createAuthLink(table.getQrCode(),table.getId());

		return authLink;
	}
}
