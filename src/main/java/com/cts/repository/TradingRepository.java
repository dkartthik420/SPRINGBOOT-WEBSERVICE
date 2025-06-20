package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.entities.Customer;
import com.cts.entities.Trading;

public interface TradingRepository extends JpaRepository<Trading, Integer>{
	
	
	 @Query("SELECT t FROM Trading t WHERE t.customer.custNo = :custNo")
	 List<Trading> findTradeByCust(@Param("custNo") int custNo);
	
}
