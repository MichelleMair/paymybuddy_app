package com.paymybuddyapp.paymybuddy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddyapp.paymybuddy.model.Transaction;
import com.paymybuddyapp.paymybuddy.repository.TransactionRepository;

@Service
public class TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Transactional(rollbackFor = Exception.class)
	public Transaction saveTransaction(Transaction transaction) {
		try {
			Transaction savedTransaction = transactionRepository.save(transaction);
			logger.info("Transaction saved successfully: {}", savedTransaction);
			return savedTransaction;
		} catch (Exception e) {
			logger.error("Failed to save transaction: {}", transaction, e);
			throw e;
		}
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
