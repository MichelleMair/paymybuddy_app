package com.paymybuddyapp.paymybuddy.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.service.ConnectionService;
import com.paymybuddyapp.paymybuddy.service.TransactionService;
import com.paymybuddyapp.paymybuddy.service.UserService;

/**
 * Relate to transfer.html (Transfer page)
 */
@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/transfer")
	public String transfer(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();
		Optional<User> currentUserOptional = userService.getUserByUsername(username);

		if (currentUserOptional.isPresent()) {
			User currentUser = currentUserOptional.get();
			model.addAttribute("user", currentUser);
			model.addAttribute("relations", connectionService.getConnectionsByUserId(currentUser.getId()));
			model.addAttribute("transactions", transactionService.getTransactionsBySender(currentUser.getId()));

			return "transfer";
		} else {
			return "redirect:/login?error=usernotfound";
		}
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

	@PostMapping("/profile/update")
	public String updateProfil(@RequestParam String password, Principal principal) {
		Optional<User> currentUserOptional = userService.getUserByUsername(principal.getName());

		if (currentUserOptional.isPresent()) {
			User currentUser = currentUserOptional.get();
			if (password != null && !password.isEmpty()) {
				currentUser.setPassword(passwordEncoder.encode(password));
				userService.saveUser(currentUser);
			}
			return "redirect:/profile";
		} else {
			return "redirect:/login?error=usernotfound";
		}

	}

	@GetMapping("/add-connection")
	public String addConnection() {
		return "add-connection";
	}
}
