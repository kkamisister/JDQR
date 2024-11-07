package com.example.backend.order.service;

import com.example.backend.order.dto.CartDto;
import com.example.backend.common.enums.SimpleResponseMessage;
import com.example.backend.order.dto.CartRequest.*;
import com.example.backend.order.dto.CartResponse.*;
import com.example.backend.order.dto.OrderResponse.*;

public interface OrderService {

	String redirectUrl(String tableName,String uuid);

	void addItem(String tableId,CartDto productInfo);
	void deleteItem(String tableId,CartDto productInfo);

	SimpleResponseMessage saveWholeOrder(String tableId);

	InitialPaymentResponseDto payForOrder(String tableId, PaymentRequestDto paymentRequestDto);

	SimpleResponseMessage finishPayment(String tableId, String tossOrderId, String status, SimpleTossPaymentRequestDto tossPaymentSimpleResponseDto);

    TotalOrderInfoResponseDto getOrderInfo(String tableId);
}
