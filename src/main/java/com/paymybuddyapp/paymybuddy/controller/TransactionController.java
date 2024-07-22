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

import com.paymybuddyapp.paymybuddy.model.Transaction;
import com.paymybuddyapp.paymybuddy.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
		Transaction savedTransaction = transactionService.saveTransaction(transaction);
		return ResponseEntity.ok(savedTransaction);
	}

	@GetMapping("/sender/{senderId}")
	public ResponseEntity<List<Transaction>> getTransactionsBySender(@PathVariable Long senderId) {
		List<Transaction> transactions = transactionService.getTransactionsBySender(senderId);

		if (transactions.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(transactions);
	}

	@GetMapping("/receiver/{receiverId}")
	public ResponseEntity<List<Transaction>> getTransactionsByReceiver(@PathVariable Long receiverId) {
		List<Transaction> transactions = transactionService.getTransactionsByReceiver(receiverId);

		if (transactions.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(transactions);
	}
}
