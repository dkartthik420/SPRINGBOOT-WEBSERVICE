package com.cts.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.CustomerDto;
import com.cts.dto.TradingDto;
import com.cts.dto.UpdateCustomerDto;
import com.cts.entities.Customer;
import com.cts.entities.Trading;
import com.cts.exception.CustomerNotFoundException;
import com.cts.exception.TradeNotFoundException;
import com.cts.repository.CustomerRepository;
import com.cts.repository.TradingRepository;
import com.cts.service.CustomerService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TradingRepository tradingRepository;

	private CustomerDto customerDto;

	private TradingDto tradingDto;

	@Override
	public List<CustomerDto> getAll() {
		return customerRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public String addCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setCustName(customerDto.getCustName());
		customer.setPanNumber(customerDto.getPanNumber());
		customer.setAadharNumber(customerDto.getAadharNumber());
		customer.setNotes(customerDto.getNotes());
		customer.setPhone(customerDto.getPhone());
		customer.setAddress(customerDto.getAddress());
		customer.setBankAccountNo(customerDto.getBankAccountNo());
		customer.setTradingAccoutNo(customerDto.getTradingAccoutNo());

		Customer savedCustomer = customerRepository.save(customer);

		if (customerDto.getTrading() != null && !customerDto.getTrading().isEmpty()) {
			List<Trading> tradingEntities = customerDto.getTrading().stream().map(tradingDto -> {
				Trading trading = new Trading();
				trading.setStockName(tradingDto.getStockName());
				trading.setQuantity(tradingDto.getQuantity());
				trading.setPrice(tradingDto.getPrice());
				trading.setStopLoss(tradingDto.getStopLoss());
				trading.setCustomer(savedCustomer);
				return trading;
			}).collect(Collectors.toList());

			tradingRepository.saveAll(tradingEntities);

			savedCustomer.setTrading(tradingEntities);
		}

		return "success";
	}
	
	
	@Override
	public CustomerDto getById(int id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with " + id + " not found"));

		List<Trading> mulTrade = tradingRepository.findTradeByCust(id);
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustNo(customer.getCustNo());
		customerDto.setCustName(customer.getCustName());
		customerDto.setAadharNumber(customer.getAadharNumber());
		customerDto.setPanNumber(customer.getPanNumber());
		customerDto.setNotes(customer.getNotes());
		customerDto.setPhone(customer.getPhone());
		customerDto.setAddress(customer.getAddress());
		customerDto.setBankAccountNo(customer.getBankAccountNo());
		customerDto.setTradingAccoutNo(customer.getTradingAccoutNo());
		customerDto.setTrading(mulTrade);
		return customerDto;
	}

	
	@Override
	public String deleteCustomerById(int id) {
		if (!customerRepository.existsById(id)) {
			throw new CustomerNotFoundException("customer with " + id + " not found");

		}
		customerRepository.deleteById(id);
		return "success";
	}
	
	@Override
	public UpdateCustomerDto updateCustomerById(UpdateCustomerDto customerDto,int id)
	{
		Customer existingCustomer=convertToEntity(getById(id));
		
		if(existingCustomer!=null)
		{
			if(customerDto.getCustName()!=null)
			{
				existingCustomer.setCustName(customerDto.getCustName());

			}
			if(customerDto.getBankAccountNo()!=null)
			{
				existingCustomer.setBankAccountNo(customerDto.getBankAccountNo());
			}
			if(customerDto.getTradingAccoutNo()!=null)
			{
				existingCustomer.setTradingAccoutNo(customerDto.getTradingAccoutNo());
			}
			if(customerDto.getPanNumber()!=null)
			{
				existingCustomer.setPanNumber(customerDto.getPanNumber());
			}
			
			if(customerDto.getAadharNumber()!=null)
			{
				existingCustomer.setAadharNumber(customerDto.getAadharNumber());
			}
			if(customerDto.getPanNumber()!=null)
			{
				existingCustomer.setPanNumber(customerDto.getPanNumber());
			}
			if(customerDto.getPanNumber()!=null)
			{
				existingCustomer.setPhone(customerDto.getPhone());
			}
		}
		Customer updatedCustomer=customerRepository.save(existingCustomer);
		
		return convertToUpdatedDto(updatedCustomer);
	}
	
	//TRADE CRUD OPERATIONS
	
	@Override
	public String addTrade(TradingDto tradeDto) {
		Trading trading=new Trading();
		trading.setCustomer(convertToEntity(getById(tradeDto.getCustId())));
		trading.setPrice(tradeDto.getPrice());
		trading.setQuantity(tradeDto.getQuantity());
		trading.setStockName(tradeDto.getStockName());
		trading.setStopLoss(tradeDto.getStopLoss());
		
		tradingRepository.save(trading);
		return "success";
	}
	

	@Override
	public String deleteTradeById(int id) {
		if (!tradingRepository.existsById(id)) {
			throw new TradeNotFoundException("Trade with id " + id + " not found");
		}
		tradingRepository.deleteById(id);

		return "success";
	}

	@Override
	public TradingDto updateTradeById(TradingDto updateTrade, int tradeId) {

		if (!tradingRepository.existsById(tradeId)) {
			throw new TradeNotFoundException("Trade with " + tradeId + " not found");
		}

//		if(updateTrade.getStopLoss()>=updateTrade.getPrice())
//		{
//			System.out.println("price equalled");
//			tradingRepository.deleteById(tradeId);
//			return null;
//		}
		
		Trading existingTrade = tradingRepository.findById(tradeId)
				.orElseThrow(() -> new TradeNotFoundException("trade with " + tradeId + " not found"));

		existingTrade.setStockName(updateTrade.getStockName());
		existingTrade.setQuantity(updateTrade.getQuantity());
		existingTrade.setPrice(updateTrade.getPrice());
		existingTrade.setStopLoss(updateTrade.getStopLoss());

		
		tradingRepository.save(existingTrade);

		TradingDto updatedDto = new TradingDto(existingTrade.getTradingNo(), existingTrade.getStockName(),
				existingTrade.getQuantity(), existingTrade.getPrice(), existingTrade.getStopLoss(),
				existingTrade.getCustomer().getCustNo());

		return updatedDto;

	}
	
	
 
	public CustomerDto convertToDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustNo(customer.getCustNo());
		customerDto.setCustName(customer.getCustName());
		customerDto.setAadharNumber(customer.getAadharNumber());
		customerDto.setPanNumber(customer.getPanNumber());
		customerDto.setNotes(customer.getNotes());
		customerDto.setPhone(customer.getPhone());
		customerDto.setAddress(customer.getAddress());
		customerDto.setBankAccountNo(customer.getBankAccountNo());
		customerDto.setTradingAccoutNo(customer.getTradingAccoutNo());
		customerDto.setTrading(customer.getTrading());
		return customerDto;
	}

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
	public UpdateCustomerDto convertToUpdatedDto(Customer customer) {
		UpdateCustomerDto customerDto = new UpdateCustomerDto();
		customerDto.setCustNo(customer.getCustNo());
		customerDto.setCustName(customer.getCustName());
		customerDto.setAadharNumber(customer.getAadharNumber());
		customerDto.setPanNumber(customer.getPanNumber());
		customerDto.setNotes(customer.getNotes());
		customerDto.setPhone(customer.getPhone());
		customerDto.setAddress(customer.getAddress());
		customerDto.setBankAccountNo(customer.getBankAccountNo());
		customerDto.setTradingAccoutNo(customer.getTradingAccoutNo());
		customerDto.setTrading(customer.getTrading());
		return customerDto;
	}

	

}
