package com.paymybuddyapp.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddyapp.paymybuddy.model.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Connection.ConnectionID>{

}
