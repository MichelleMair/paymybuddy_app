package com.paymybuddyapp.paymybuddy.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddyapp.paymybuddy.model.Transaction;
import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.service.TransactionService;
import com.paymybuddyapp.paymybuddy.service.UserService;

@Controller
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).orElse(null);

		if (user != null) {
			List<Transaction> transactions = transactionService.getTransactionsBySender(user.getId());
			model.addAttribute("transactions", transactions);
			model.addAttribute("user", user);
		}
		return "transfer";
	}

	@PostMapping("/transfer")
	public String transfer(@RequestParam Long receiverId, @RequestParam String description, @RequestParam double amount,
			Principal principal) {
		User sender = userService.getUserByUsername(principal.getName()).orElse(null);
		User receiver = userService.getUserById(receiverId).orElse(null);

		if (sender != null && receiver != null && sender.getId() != receiver.getId()) {
			Transaction transaction = new Transaction();
			transaction.setSender(sender);
			transaction.setReceiver(receiver);
			transaction.setDescription(description);
			transaction.setAmount(amount);
			transactionService.saveTransaction(transaction);
		}
		return "redirect:/transfer";
	}

}
