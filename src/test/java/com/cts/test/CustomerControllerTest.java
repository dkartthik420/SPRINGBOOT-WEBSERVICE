package com.cts.test;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.service.impl.*;
import com.cts.dto.CustomerDto;
import com.cts.dto.TradingDto;
import com.cts.entities.Customer;
import com.cts.entities.Trading;
import com.cts.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	CustomerService cs;

	@Autowired
	ObjectMapper mapper;

	List<CustomerDto> customers = new ArrayList<>();

	List<Trading> trade1 = new ArrayList<>();
	List<Trading> trade2 = new ArrayList<>();

	CustomerDto cust1 = new CustomerDto(1, "kartthik", "KHQPK6649E", "851851864642", "good customer",
			(long) 8341257360L, "Ap", "BANKIND1", "TRADE001", trade1);
	CustomerDto cust2 = new CustomerDto(2, "chinku", "WEJDK6728E", "632783832829", "good customer", (long) 8341257369L,
			"Ap", "BANKIND2", "TRADE002", trade2);

	public Customer convertToEntity(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setCustNo(customerDto.getCustNo());
		customer.setCustName(customerDto.getCustName());
		customer.setPanNumber(customerDto.getPanNumber());
		customer.setAadharNumber(customerDto.getAadharNumber());
		customer.setNotes(customerDto.getNotes());
		customer.setPhone(customerDto.getPhone());
		customer.setAddress(customerDto.getAddress());
		customer.setBankAccountNo(customerDto.getBankAccountNo());
		customer.setTradingAccoutNo(customerDto.getTradingAccoutNo());
		customer.setTrading(customerDto.getTrading());
		return customer;
	}

	public TradingDto convertTradinToDto(Trading trade) {
		TradingDto trading = new TradingDto(trade.getTradingNo(), trade.getStockName(), trade.getQuantity(),
				trade.getPrice(), trade.getStopLoss(), trade.getCustomer().getCustNo());
		return trading;

	}

	Customer customer1 = convertToEntity(cust1);
	Customer customer2 = convertToEntity(cust2);

	Trading trade1C1 = new Trading(1, "HP", 2, 22.0, 10.0, customer1);
	Trading trade2C1 = new Trading(2, "CTS", 3, 30.0, 9.0, customer1);

	Trading trade1C2 = new Trading(3, "HP", 2, 22.0, 10.0, customer2);
	Trading trade2C2 = new Trading(4, "CTS", 3, 30.0, 9.0, customer2);

	@BeforeEach
	void each() {
		trade1 = List.of(trade1C1, trade2C1);
		trade2 = List.of(trade1C2, trade2C2);

		customers.add(cust1);
		customers.add(cust2);
	}

	@Test
	@WithMockUser
	void testGetCustomers() throws Exception {
		when(cs.getAll()).thenReturn(customers);

		mockMvc.perform(get("/api/Tradingcompany")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2));
		verify(cs, times(1)).getAll();
	}

	@Test
	@WithMockUser
	void testGetCustomerById() throws Exception {
		when(cs.getById(1)).thenReturn(cust1);

		mockMvc.perform(get("/api/Tradingcompany/1")).andExpect(status().isOk())
				// .andExpect(jsonPath("$.size()").value(1))
				.andExpect(jsonPath("$.custName").value("kartthik"));
		verify(cs, times(1)).getById(1);

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void testAdd() throws Exception {

		var jsonCustomer = mapper.writeValueAsString(cust1);

		mockMvc.perform(post("/api/Tradingcompany/save").content(jsonCustomer).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void testDeleteById() throws Exception {
		when(cs.deleteById(1)).thenReturn("success");

		mockMvc.perform(delete("/api/Tradingcompany/1")).andExpect(status().isAccepted());
		verify(cs, times(1)).deleteById(1);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void testDeleteTradeById() throws Exception {
		when(cs.deleteTradeById(1)).thenReturn("success");

		mockMvc.perform(delete("/api/Tradingcompany/trade/1")).andExpect(status().isAccepted());
		verify(cs, times(1)).deleteTradeById(1);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testUpdate() throws Exception {

		TradingDto trading = convertTradinToDto(trade1C1);
		when(cs.updateTraById(trading, 1)).thenReturn(trading);
		
		
		var jsonTrading = mapper.writeValueAsString(trading);
		
		mockMvc.perform(put("/api/Tradingcompany/1").content(jsonTrading).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
		

		verify(cs, times(1)).updateById(trading, 1);
	}

}
