package com.paymybuddyapp.paymybuddy.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddyapp.paymybuddy.model.Connection;
import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.service.ConnectionService;
import com.paymybuddyapp.paymybuddy.service.UserService;

@Controller
@RequestMapping("/connections")
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private UserService userService;

	@PostMapping("/add-connection")
	public String addConnection(@RequestParam String email, Principal principal, Model model) {

		User user = userService.getUserByUsername(principal.getName()).orElse(null);

		User connection = userService.getUserByEmail(email).orElse(null);

		if (user != null && connection != null && !user.getId().equals(connection.getId())) {
			Connection conn = new Connection(new Connection.ConnectionID(user.getId(), connection.getId()), user,
					connection);
			connectionService.saveConnection(conn);
		}

		return "redirect:/connections/add-connection";
	}

	@GetMapping("/add-connection")
	public String addConnectionPage(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).orElse(null);

		model.addAttribute("user", user);
		return "add-connection";
	}
}
