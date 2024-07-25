package com.paymybuddyapp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddyapp.paymybuddy.model.Connection;
import com.paymybuddyapp.paymybuddy.repository.ConnectionRepository;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceTest {

	@Mock
	private ConnectionRepository connectionRepository;

	@InjectMocks
	private ConnectionService connectionService;

	@Test
	public void testSaveCoonection() {
		// ARRANGE
		Connection connection = new Connection();

		when(connectionRepository.save(connection)).thenReturn(connection);

		// ACT
		Connection savedConnection = connectionService.saveConnection(connection);

		// ASSERT
		assertThat(savedConnection).isEqualTo(connection);

	}

	@Test
	public void testGetConnectionsByUserId() {
		// ARRANGE
		Long userId = 1L;
		Connection connection = new Connection();

		when(connectionRepository.findByUserId(userId)).thenReturn(List.of(connection));

		// ACT
		List<Connection> connections = connectionService.getConnectionsByUserId(userId);

		// ASSERT
		assertThat(connections).contains(connection);
	}

}
