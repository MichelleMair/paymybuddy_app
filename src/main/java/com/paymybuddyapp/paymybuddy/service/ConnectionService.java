package com.paymybuddyapp.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddyapp.paymybuddy.model.Connection;
import com.paymybuddyapp.paymybuddy.repository.ConnectionRepository;

@Service
public class ConnectionService {

	@Autowired
	private ConnectionRepository connectionRepository;

	public Connection saveConnection(Connection connection) {
		return connectionRepository.save(connection);
	}

	public List<Connection> getConnectionsByUserId(Long userId) {
		return connectionRepository.findByUserId(userId);
	}
}
