package com.cts.dto;

import com.cts.entities.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingDto {
	private int tradingNo;
	
	@NotNull
	private String stockName;
	
	@NotNull
	private int quantity;
	
	@NotNull
	private Double price;
	
	@NotNull
	private Double stopLoss;
	
	private int custId;
	
//	private Customer customer;
}
