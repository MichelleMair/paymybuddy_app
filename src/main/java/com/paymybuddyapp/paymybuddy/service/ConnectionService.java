package com.paymybuddyapp.paymybuddy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddyapp.paymybuddy.model.Connection;
import com.paymybuddyapp.paymybuddy.repository.ConnectionRepository;

@Service
public class ConnectionService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private ConnectionRepository connectionRepository;

	public Connection saveConnection(Connection connection) {
		return connectionRepository.save(connection);
	}

	public List<Connection> getConnectionsByUserId(Long userId) {
		logger.info("1. Before saving user in UserService: {}", userId);

		List<Connection> conn = connectionRepository.findByUserId(userId);

		logger.info("2. After saving user in UserService: {}", userId);
		return conn;
	}

	public boolean existsByUserAndConnection(Long userId, Long connectionId) {
		return connectionRepository.findByUserIdAndConnectionId(userId, connectionId).isPresent();
	}
}
