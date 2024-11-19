package com.example.backend.common.event;

import org.springframework.context.ApplicationEvent;

import com.example.backend.common.enums.Operator;

public class CartEvent extends ApplicationEvent {

	private String tableId;
	private String sessionId;
	private Operator operator;

	public CartEvent(String tableId,String sessionId,Operator operator){
		super(tableId);
		this.tableId = tableId;
		this.sessionId = sessionId;
		this.operator = operator;
	}

	public String getTableId(){
		return tableId;
	}
	public String getSessionId(){return sessionId;}
	public Operator getOperator() {
		return operator;
	}
}
