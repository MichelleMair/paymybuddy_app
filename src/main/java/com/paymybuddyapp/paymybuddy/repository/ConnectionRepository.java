package com.paymybuddyapp.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddyapp.paymybuddy.model.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Connection.ConnectionID> {

	List<Connection> findByUserId(Long userId);
}
