package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.cts.dto.CustomerDto;
import com.cts.dto.TradingDto;
import com.cts.dto.UpdateCustomerDto;
import com.cts.entities.Customer;
import com.cts.entities.Trading;
import com.cts.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/api/Tradingcompany")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public List<CustomerDto> getCustomers() {
		return customerService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable int id) {
		return new ResponseEntity<CustomerDto>(customerService.getById(id), HttpStatus.OK);
	}

	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerDto customerDto) {

		customerService.addCustomer(customerDto);
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}
	
	@PostMapping("/save/trading")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addTrading(@Valid @RequestBody TradingDto tradingDto){
		customerService.addTrade(tradingDto);
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}
	

	@DeleteMapping("trade/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteTradingById(@PathVariable int id) {

		customerService.deleteTradeById(id);
		return new ResponseEntity<String>("Accepted", HttpStatus.ACCEPTED);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCustomerById(@PathVariable int id) {

		customerService.deleteCustomerById(id);
		
		return new ResponseEntity<String>("Accepted", HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("{tradeId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TradingDto> update(@Valid @RequestBody TradingDto updateTrade, @PathVariable int tradeId) {
		var result = customerService.updateTradeById(updateTrade, tradeId);
		return new ResponseEntity<TradingDto>(result, HttpStatus.CREATED);
	}
	
	@PutMapping("/customer/{customerId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UpdateCustomerDto> update(@Valid @RequestBody UpdateCustomerDto updateCustomerDto, @PathVariable int customerId) {
		var result = customerService.updateCustomerById(updateCustomerDto,customerId);
		return new ResponseEntity<UpdateCustomerDto>(result, HttpStatus.CREATED);
	}
	

}
