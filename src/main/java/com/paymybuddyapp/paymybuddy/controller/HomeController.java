package com.paymybuddyapp.paymybuddy.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/profile")
	public String profile(Model model) {
		// Récupération de l'utilisateur actuellement authentifié
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// Recherche de l'utilisateur dans la BDD
		Optional<User> currentUserOptional = userService.getUserByUsername(username);

		if (currentUserOptional.isPresent()) {
			User currentUser = currentUserOptional.get();
			model.addAttribute("user", currentUser);
			return "profile";
		} else {
			return "redirect:/login?error=usernotfound";
		}
	}

	@GetMapping("/add-connection")
	public String addConnection() {
		return "add-connection";
	}
}
