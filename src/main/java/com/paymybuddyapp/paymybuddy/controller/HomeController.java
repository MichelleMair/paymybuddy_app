package com.paymybuddyapp.paymybuddy.controller;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

		String email = authentication.getName();
		Optional<User> currentUserOptional = userService.getUserByEmail(email);

		if (currentUserOptional.isPresent()) {
			User currentUser = currentUserOptional.get();
			model.addAttribute("user", currentUser);
			model.addAttribute("relations", connectionService.getConnectionsByUserId(currentUser.getId()));
			model.addAttribute("transactions", transactionService.getTransactionsBySender(currentUser.getId()));

			logger.info("Loaded transfer page for user: {}", currentUser.getEmail());
			return "transfer";
		} else {
			logger.error("User not found for email: {}", email);
			return "redirect:/login?error=usernotfound";
		}
	}

	@GetMapping("/profile")
	public String profile(Model model) {
		// Récupération de l'utilisateur actuellement authentifié
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		// Recherche de l'utilisateur dans la BDD
		Optional<User> currentUserOptional = userService.getUserByEmail(email);

		if (currentUserOptional.isPresent()) {
			User currentUser = currentUserOptional.get();
			model.addAttribute("user", currentUser);
			return "profile";
		} else {
			return "redirect:/login?error=usernotfound";
		}
	}

	@PostMapping("/profile/update")
	public String updateProfil(@RequestParam String password, Principal principal, Model model) {
		try {
			Optional<User> currentUserOptional = userService.getUserByEmail(principal.getName());

			if (currentUserOptional.isPresent()) {
				User currentUser = currentUserOptional.get();
				if (password != null && !password.isEmpty()) {
					currentUser.setPassword(passwordEncoder.encode(password));
					userService.saveUser(currentUser);
					model.addAttribute("successMessage", "Mot de passe modifié avec succès! ");
				} else {
					model.addAttribute("errorMessage", "Le mot de passe ne peut être vide.");
				}
				model.addAttribute("user", currentUser);
			} else {
				model.addAttribute("errorMessage", "Utilisateur non trouvé.");
				return "redirect:/login?error=usernotfound";
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Une erreur s'est produite. Veuillez réessayer. ");
		}
		return "profile";
	}

	@GetMapping("/add-connection")
	public String addConnection() {
		return "add-connection";
	}
}
