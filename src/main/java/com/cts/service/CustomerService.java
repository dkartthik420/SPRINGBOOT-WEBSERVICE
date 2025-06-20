package com.cts.service;
import com.cts.dto.CustomerDto;
import com.cts.dto.TradingDto;
import com.cts.dto.UpdateCustomerDto;
import com.cts.entities.Customer;
import com.cts.entities.Trading;

import java.util.List;


public interface CustomerService {
	List<CustomerDto> getAll();
	String addCustomer(CustomerDto customer);
	CustomerDto getById(int id);
	String deleteCustomerById(int id);
	TradingDto updateTradeById(TradingDto updateTrade, int tradeId);
	String deleteTradeById(int id);
	String addTrade(TradingDto tradeDto);
	UpdateCustomerDto updateCustomerById(UpdateCustomerDto customerDto,int id);
	
}
