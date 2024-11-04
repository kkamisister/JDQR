package com.example.backend.order.service;

import com.example.backend.order.dto.CartDto;
import com.example.backend.common.enums.SimpleResponseMessage;
import com.example.backend.order.dto.CartRequest.*;

public interface OrderService {

	String redirectUrl(String tableName,String uuid);

	void addItem(String tableId,CartDto productInfo);
	void deleteItem(String tableId,CartDto productInfo);

	SimpleResponseMessage saveWholeOrder(String tableId);

	SimpleResponseMessage payForOrder(String tableId, PaymentRequestDto paymentRequestDto);
}
