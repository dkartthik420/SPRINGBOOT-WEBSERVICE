package com.cts.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Trading {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tradingNo;
	
	
	private String stockName;
	

	private int quantity;
	

	private Double price;
	
	private Double stopLoss;
	
	
	@JsonBackReference
	
	//relation
	@ManyToOne(fetch = FetchType.EAGER)
	
	//create the foregin key name as cust_no
	@JoinColumn(name="cust_no") 
	
	private Customer customer;
}
