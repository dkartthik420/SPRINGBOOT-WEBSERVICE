package com.cts.dto;

import java.util.List;

import com.cts.entities.Trading;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

	private int custNo;
	
	//validation
	
	@Pattern(regexp = "[A-Za-z\s]+",message = "Name should only be alphabeticals")
	@NotNull
	private String custName;
	
	@NotNull
	@Column(unique = true)
	@Size(max = 10,message = "Should not be more than 10 characters")
	private String panNumber;
	
	@Size(max = 12,message = "Should not be more than 12 characters")
	@NotNull
	@Column(unique = true)
	private String aadharNumber;

	@Size(max = 250,message = "Should not be more than 250 characters")
	private String Notes;
	
	@NotNull
	@Max(value = 9999999999L, message = "Phone number should not be more than 10 digits")
	@Min(value = 1000000000L, message = "Phone number should be exactly 10 digits")
	private Long phone;
	
	@Size(max = 250,message = "Should not be more than 250 characters")
	@NotNull
	private String address;
	
	@NotNull
	@Column(unique = true)
	private String bankAccountNo;
	
	@NotNull
	@Column(unique = true)
	private String tradingAccoutNo;
	
	@Valid
	private List<Trading> trading;
}
