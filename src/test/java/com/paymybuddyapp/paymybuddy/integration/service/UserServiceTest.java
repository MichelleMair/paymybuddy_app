package com.paymybuddyapp.paymybuddy.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;
import com.paymybuddyapp.paymybuddy.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ComponentScan(basePackageClasses = { UserService.class, UserRepository.class })
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	public void testSaveUser() {

		// GIVEN
		User user = new User();
		user.setUsername("testUser");
		user.setEmail("testUser@example.com");
		user.setPassword("password");

		// WHEN
		userService.saveUser(user);

		// THEN
		Optional<User> foundUser = userRepository.findByUsername("testUser");

		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getEmail()).isEqualTo("testUser@example.com");
	}
}
