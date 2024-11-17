package com.example.backend.common.event;

import org.springframework.context.ApplicationEvent;

import com.example.backend.common.enums.Operator;

public class CartEvent extends ApplicationEvent {

	private String tableId;
	private Operator operator;

	public CartEvent(String tableId,Operator operator){
		super(tableId);
		this.tableId = tableId;
		this.operator = operator;
	}

	public String getTableId(){
		return tableId;
	}
	public Operator getOperator(){
		return operator;
	}
}
