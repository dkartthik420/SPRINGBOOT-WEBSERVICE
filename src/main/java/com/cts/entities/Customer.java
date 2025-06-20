package com.cts.entities;
import java.util.List;

import com.cts.dto.CustomerDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")

//custome entity class

public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int custNo;
	
	private String custName; 
	

	private String panNumber;
	
	private String aadharNumber;

	private String Notes;
	
	private Long phone;
	
	private String address;
	
	private String bankAccountNo;
	 
	private String tradingAccoutNo;
	
	
	
	@JsonManagedReference
	//relation
	//@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Trading> trading;
}