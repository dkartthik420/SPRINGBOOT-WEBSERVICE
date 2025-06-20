package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Role;
import com.cts.entities.User;

public interface RoleRepository extends  JpaRepository<Role, Integer>{
	Optional<Role> findByName(String name);
	boolean existsByName(String name);
}