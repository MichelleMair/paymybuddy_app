package com.paymybuddyapp.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;
import com.paymybuddyapp.paymybuddy.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DBRider
public class UserServiceIT {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	@DataSet(cleanBefore = true, cleanAfter = true)
	public void testSaveUser() {

		// GIVEN (Using System.currentTimeMillis to generate unique email for every
		// running test to avoid duplications)
		User user = new User();
		user.setUsername("testUser" + System.currentTimeMillis());
		user.setEmail("testUser" + System.currentTimeMillis() + "testUser@example.com");
		user.setPassword("password");

		// WHEN
		userService.saveUser(user);

		// THEN
		Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	@Transactional
	@DataSet(cleanBefore = true, cleanAfter = true)
	public void testCreateUser() {

		// GIVEN
		User user = new User();
		user.setUsername("createTestUser" + System.currentTimeMillis());
		user.setEmail("createTestUser" + System.currentTimeMillis() + "testUser@example.com");
		user.setPassword("password");

		// WHEN
		User savedUser = userService.saveUser(user);

		// THEN
		Optional<User> foundUser = userRepository.findById(savedUser.getId());
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getUsername()).isEqualTo(user.getUsername());
		assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
	}

}
