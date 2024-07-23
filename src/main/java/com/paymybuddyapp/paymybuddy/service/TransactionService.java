package com.paymybuddyapp.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddyapp.paymybuddy.model.Transaction;
import com.paymybuddyapp.paymybuddy.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Transactional(rollbackFor = Exception.class)
	public Transaction saveTransaction(Transaction transaction) {

		return transactionRepository.save(transaction);
	}

	@Transactional(readOnly = true)
	public List<Transaction> getTransactionsBySender(Long senderId) {
		return transactionRepository.findBySenderId(senderId);
	}

	@Transactional(readOnly = true)
	public List<Transaction> getTransactionsByReceiver(Long receiverId) {
		return transactionRepository.findByReceiverId(receiverId);
	}
}
