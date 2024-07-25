package com.paymybuddyapp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveUser() {
		// ARRANGE
		User user = new User();
		user.setUsername("JohnDoe");
		user.setEmail("johndoe@example.com");
		user.setPassword("johndoepassword");

		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(user)).thenReturn(user);

		// ACT
		User savedUser = userService.saveUser(user);

		// ASSERT
		assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
	}

	@Test
	public void testGetUserByUsername() {
		// ARRANGE
		String username = "JohnDoe";
		User user = new User();
		user.setUsername(username);

		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

		// ACT
		Optional<User> foundUser = userService.getUserByUsername(username);

		// ASSERT
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getUsername()).isEqualTo(username);
	}
}
