package com.paymybuddyapp.paymybuddy.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		logger.info("Saving user : {}", user);
		return userRepository.save(user);
	}

	public Optional<User> getUserByUsername(String username) {
		logger.info("Finding user by username: {}", username);
		return userRepository.findByUsername(username);
	}

	public Optional<User> getUserById(Long id) {
		logger.info("Finding user by id: {}", id);
		return userRepository.findById(id);
	}
}
