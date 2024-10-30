package com.example.backend.order.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductOption {

	private String name;
	private int price;

	ProductOption(String name,int price){
		this.name = name;
		this.price = price;
	}
}
