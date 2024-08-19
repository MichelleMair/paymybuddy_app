package com.paymybuddyapp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private UserRepository userRepository;

	private UserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		// création du mock du repository
		userRepository = Mockito.mock(UserRepository.class);
		userService = new UserService(userRepository); // injection manuelle du mock du repository
	}

	@Test
	public void testSaveUser() {
		// ARRANGE
		User user = new User();
		user.setUsername("JohnDoe");
		user.setEmail("johndoe@example.com");
		user.setPassword("encodedPassword");

		System.out.println("1. Get user before saving and calling repository: " + user);

		// SImulation sauvegarde du nouvel utilisateur dans le mock du repository
		when(userRepository.save(user)).thenReturn(user);

		// ACT
		User savedUser = userService.saveUser(user);

		System.out.println("2. Get usersavec successfully in mock repository: " + savedUser);

		// Vérification que le mock a bien été appelé
		verify(userRepository).save(user);

		// ASSERT
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("JohnDoe");
		assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
		assertThat(savedUser.getEmail()).isEqualTo("johndoe@example.com");
	}

	@Test
	public void testGetUserByUsername() {
		// ARRANGE
		String username = "JohnDoe";
		User user = new User();
		user.setUsername(username);
		user.setEmail("johndoe@example.com");
		user.setPassword("encodedPassword");

		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

		// ACT
		Optional<User> foundUser = userService.getUserByUsername(username);

		// Vérification que le mock a bien été appelé
		verify(userRepository).findByUsername(username);

		// ASSERT
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getUsername()).isEqualTo(username);
	}

	@Test
	public void testGetUserByEmail() {
		// ARRANGE
		String email = "johndoe@example.com";
		User user = new User();
		user.setUsername("JohnDoe");
		user.setEmail(email);
		user.setPassword("encodedPassword");

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		// ACT
		Optional<User> foundUser = userService.getUserByEmail(email);

		// Vérification que le mock a bien été appelé
		verify(userRepository).findByEmail(email);

		// ASSERT
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getEmail()).isEqualTo(email);
	}

}
