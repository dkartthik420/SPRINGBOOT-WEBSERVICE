package com.cts.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.runtime.TemplateRuntime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.dto.CustomerDto;
import com.cts.dto.TradingDto;
import com.cts.entities.Customer;
import com.cts.entities.Trading;
import com.cts.exception.CustomerNotFoundException;
import com.cts.exception.TradeNotFoundException;
import com.cts.repository.CustomerRepository;
import com.cts.repository.TradingRepository;
import com.cts.service.impl.CustomerServiceImpl;
@SpringBootTest
class CustomerServiceImplTest {

	@Mock
	CustomerRepository customerRepository;
	

    @Mock
    TradingRepository tradingRepository;
	
	@InjectMocks
	CustomerServiceImpl customerService;
	
	
	List<Customer> customers=new ArrayList<>();
	
	List<Trading> trade1=new ArrayList<>();
	List<Trading> trade2=new ArrayList<>();
	
	Customer cust1=new Customer(1, "kartthik", "KHQPK6649E", "8518-5186-4642", "good customer",(long) 834125, "Ap", "BANKIND1", "TRADE001", trade1);
	Customer cust2=new Customer(2, "chinku", "WEJDK6728E", "6327-8383-2829", "good customer",(long) 7369, "Ap", "BANKIND2", "TRADE002", trade2);
	
	
	
	Trading trade1C1=new Trading(1, "HP", 2, 22.0, 10.0, cust1);
	Trading trade2C1=new Trading(2, "CTS", 3, 30.0, 9.0, cust1);
	
	
	Trading trade1C2=new Trading(3, "HP", 2, 22.0, 10.0, cust2);
	Trading trade2C2=new Trading(4, "CTS", 3, 30.0, 9.0, cust2);
	
	Trading temp=new Trading(69, "hexaware", 10, 20.0, 19.0, cust1);
	
	
			
	@BeforeEach
	void each()
	{
		trade1=List.of(trade1C1,trade2C1);
		trade2=List.of(trade1C2,trade2C2);
		
		customers=List.of(cust1,cust2);		
	}
	
	
	
	@Test
	void testGetAll() {
		when(customerRepository.findAll()).thenReturn(customers);
		var result=customerService.getAll();
		assertEquals(2, result.size());
		assertEquals("chinku", result.get(1).getCustName());
		verify(customerRepository,times(1)).findAll();
	}	
	
	@Test
	void testAddCustomer() {
		
		CustomerDto customer=new CustomerDto();
		customer.setCustName(cust1.getCustName());
        customer.setPanNumber(cust1.getPanNumber());
        customer.setAadharNumber(cust1.getAadharNumber());
        customer.setNotes(cust1.getNotes()); 
        customer.setPhone(cust1.getPhone());
        customer.setAddress(cust1.getAddress());
        customer.setBankAccountNo(cust1.getBankAccountNo());
        customer.setTradingAccoutNo(cust1.getTradingAccoutNo());

        
//        TradingDto tradingDto=new TradingDto();
//        tradingDto.setStockName(trade1c1);
        
		when(customerRepository.save(any(Customer.class))).thenReturn(cust1);
//		when(customerRepository.saveAll(any(Trading.class))).thenReturn(trade1);
		var result=customerService.addCustomer(customer);
		assertEquals("success",result);
		verify(customerRepository,times(1)).save(any(Customer.class));
		
		
	}
	
	@Test
	void testGetById() {
		CustomerDto customer=new CustomerDto();
		
		customer.setCustName(cust1.getCustName());
        customer.setPanNumber(cust1.getPanNumber());
        customer.setAadharNumber(cust1.getAadharNumber());
        customer.setNotes(cust1.getNotes()); 
        customer.setPhone(cust1.getPhone());
        customer.setAddress(cust1.getAddress());
        customer.setBankAccountNo(cust1.getBankAccountNo());
        customer.setTradingAccoutNo(cust1.getTradingAccoutNo());
        customer.setTrading(trade1);

		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(cust1));
		var result=customerService.getById(1);
		assertEquals("kartthik", result.getCustName());
		verify(customerRepository,times(1)).findById(anyInt());
		
	}

	@Test
	void testDeleteByIdWhenCustomerFound() {
		when(customerRepository.existsById(anyInt())).thenReturn(true);
		var result=customerService.deleteById(1);
		assertEquals("success", result);
		verify(customerRepository,times(1)).deleteById(1);
		verify(customerRepository,times(1)).existsById(anyInt());
	}
	
	@Test
	void testDeleteByIdWhenCustomerNotFound() {
		when(customerRepository.existsById(anyInt())).thenReturn(false);
		assertThrows(CustomerNotFoundException.class,()->customerService.deleteById(1));
		verify(customerRepository,times(1)).existsById(anyInt());
		verify(customerRepository,never()).deleteById(anyInt());
	}
	
	
	@Test
	void testDeleteByIdWhenTradeFound() {
		when(tradingRepository.existsById(anyInt())).thenReturn(true);
		var result=customerService.deleteTradeById(4);
		assertEquals("success", result);
		verify(tradingRepository,times(1)).existsById(anyInt());
		verify(tradingRepository,times(1)).deleteById(4);
	}
	@Test
	void testDeleteByIdWhenTradeNotFound() {
		when(tradingRepository.existsById(anyInt())).thenReturn(false);
		assertThrows(TradeNotFoundException.class,()->customerService.deleteTradeById(1));
		verify(tradingRepository,times(1)).existsById(anyInt());
		verify(tradingRepository,never()).deleteById(anyInt());
	}
	
	

	@Test
	void testUpdateByIdWhenCustomerFound() {
		TradingDto tradingnDto=new TradingDto(temp.getTradingNo(), temp.getStockName(), temp.getQuantity(), temp.getPrice(), temp.getStopLoss(),temp.getCustomer().getCustNo());
		
		when(tradingRepository.existsById(anyInt())).thenReturn(true);
		when(tradingRepository.findById(anyInt())).thenReturn(Optional.of(trade2C1));
		when(tradingRepository.save(any(Trading.class))).thenAnswer(x->x.getArgument(0));
		
		var trade=customerService.updateById(tradingnDto, 69);
		
		assertNotNull(trade);
		assertEquals("hexaware",trade.getStockName());
		
		verify(tradingRepository,times(1)).existsById(anyInt());
		verify(tradingRepository,times(1)).findById(anyInt());
		verify(tradingRepository,times(1)).save(any(Trading.class));
		
		
		
	}
	@Test
	void testUpdateByIdWhenCustomerNotFound() {
		TradingDto tradingDto=new TradingDto();
		when(tradingRepository.existsById(anyInt())).thenReturn(false);
		
		assertThrows(TradeNotFoundException.class,()->customerService.updateById(tradingDto,0));
		
		verify(tradingRepository,times(1)).existsById(anyInt());
		verify(tradingRepository,never()).findById(1);
		verify(tradingRepository,never()).save(any(Trading.class));
		
	}
	

}
