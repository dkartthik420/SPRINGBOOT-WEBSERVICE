package com.cts.dto;

import jakarta.validation.constraints.NotNull;

public class CommonDto {

	private int tradingNo;
	
	@NotNull
	private String stockName;
	
	@NotNull
	private int quantity;
	
	@NotNull
	private Double price;
	
	@NotNull
	private Double stopLoss;
	
	@NotNull
	private int custNo;
	
	
}
