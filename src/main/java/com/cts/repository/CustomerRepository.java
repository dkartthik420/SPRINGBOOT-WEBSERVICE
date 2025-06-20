package com.cts.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	
}
