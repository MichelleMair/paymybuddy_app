package com.paymybuddyapp.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.paymybuddyapp.paymybuddy.model.Connection;
import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.ConnectionRepository;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;
import com.paymybuddyapp.paymybuddy.service.ConnectionService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DBRider
public class ConnectionServiceIT {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private ConnectionRepository connectionRepository;

	@Test
	@Transactional
	@DataSet(cleanBefore = true, cleanAfter = true)
	public void testSaveConnection() {
		// GIVEN
		User user = new User(null, "user1", "user1@example.com", "password1");

		User connection = new User(null, "user2", "user2@example.com", "password2");

		userRepository.save(user);
		userRepository.save(connection);

		Connection.ConnectionID connectionID = new Connection.ConnectionID(user.getId(), connection.getId());
		Connection newConnection = new Connection(connectionID, user, connection);

		// WHEN
		connectionService.saveConnection(newConnection);

		// THEN
		Optional<Connection> foundConnection = connectionRepository.findById(connectionID);
		assertThat(foundConnection).isPresent();
		assertThat(foundConnection.get().getUser().getUsername()).isEqualTo("user1");
		assertThat(foundConnection.get().getConnection().getUsername()).isEqualTo("user2");
	}

	@Test
	@Transactional
	@DataSet(cleanBefore = true, cleanAfter = true)
	public void testGetConnectionsByUserId() {

		// GIVEN
		User user = new User(null, "user1", "user1@example.com", "password1");
		User connection1 = new User(null, "user2", "user2@example.com", "password2");
		User connection2 = new User(null, "user3", "user3@example.com", "password3");

		userRepository.save(user);
		userRepository.save(connection1);
		userRepository.save(connection2);

		Connection conn1 = new Connection(new Connection.ConnectionID(user.getId(), connection1.getId()), user,
				connection1);
		Connection conn2 = new Connection(new Connection.ConnectionID(user.getId(), connection2.getId()), user,
				connection2);
		connectionRepository.save(conn1);
		connectionRepository.save(conn2);

		// WHEN
		List<Connection> connections = connectionService.getConnectionsByUserId(user.getId());

		// THEN
		assertThat(connections).hasSize(2);
		assertThat(connections.get(0).getConnection().getUsername()).isEqualTo("user2");
		assertThat(connections.get(1).getConnection().getUsername()).isEqualTo("user3");
	}

}
