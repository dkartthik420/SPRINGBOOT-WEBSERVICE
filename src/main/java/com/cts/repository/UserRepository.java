package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsernameOrEmail(String username,String email);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	boolean existsByUsernameOrEmail(String username,String email);
}
