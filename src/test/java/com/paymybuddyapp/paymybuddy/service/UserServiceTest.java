package com.paymybuddyapp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	public void testSaveUser() {
		// ARRANGE
		User user = new User();
		user.setUsername("JohnDoe");
		user.setEmail("johndoe@example.com");
		user.setPassword("encodedPassword");

		// Simule la sauvegarde de l'utilisateur dans le repository
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		User savedUser = userService.saveUser(user);

		// Vérifie que le mock a bien été appelé
		verify(userRepository).save(any(User.class));

		// ASSERT
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("JohnDoe");
		assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
		assertThat(savedUser.getEmail()).isEqualTo("johndoe@example.com");
	}
}
