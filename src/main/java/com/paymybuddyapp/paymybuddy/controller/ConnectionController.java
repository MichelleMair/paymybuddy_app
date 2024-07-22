package com.paymybuddyapp.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddyapp.paymybuddy.model.Connection;
import com.paymybuddyapp.paymybuddy.service.ConnectionService;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

	@PostMapping
	public ResponseEntity<Connection> createCoonection(@RequestBody Connection connection) {

		Connection savedConnection = connectionService.saveConnection(connection);
		return ResponseEntity.ok(savedConnection);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Connection>> getConnectionsByUserId(@PathVariable Long userId) {

		List<Connection> connections = connectionService.getConnectionsByUserId(userId);
		if (connections.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(connections);
	}
}
