package com.example.backend.common.event;

import org.springframework.context.ApplicationEvent;

public class CartEvent extends ApplicationEvent {

	private String tableId;

	public CartEvent(String tableId){
		super(tableId);
		this.tableId = tableId;
	}

	public String getTableId(){
		return tableId;
	}
}
