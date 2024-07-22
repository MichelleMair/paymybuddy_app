package com.paymybuddyapp.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddyapp.paymybuddy.model.Transaction;
import com.paymybuddyapp.paymybuddy.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction saveTransaction(Transaction transaction) {

		return transactionRepository.save(transaction);
	}

	public List<Transaction> getTransactionsBySender(Long senderId) {
		return transactionRepository.findBySenderId(senderId);
	}

	public List<Transaction> getTransactionsByReceiver(Long receiverId) {
		return transactionRepository.findByReceiverId(receiverId);
	}
}
