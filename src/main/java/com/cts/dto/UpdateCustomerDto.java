package com.cts.dto;

import java.util.List;

import com.cts.entities.Trading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCustomerDto {

	private int custNo;

	private String custName;

	private String panNumber;

	private String aadharNumber;

	private String Notes;

	private Long phone;

	private String address;

	private String bankAccountNo;

	private String tradingAccoutNo;
	
	private List<Trading> trading;
}
