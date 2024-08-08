package com.paymybuddyapp.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.service.UserService;

@Controller
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error, Model model) {

		if (error != null) {
			model.addAttribute("error", "Identifiant ou mot de passe incorrect");
		}
		logger.info("Navigating to login page");

		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String processRegistration(@RequestParam String username, @RequestParam String email,
			@RequestParam String password) {

		logger.info("Attempting to register user with username: {}", username);

		if (userService.getUserByUsername(username).isPresent()) {
			logger.warn("Username {} is already taken.", username);
			return "redirect:/register?error=username";
		}
		if (userService.getUserByEmail(email).isPresent()) {
			logger.warn("Email {} is already registered.", email);
			return "redirect:/register?error=email";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		logger.info("Encoded password: {}", user.getPassword());
		userService.saveUser(user);

		logger.info("User {} registered successfully.", username);

		return "redirect:/login";
	}
}
