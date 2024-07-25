package com.paymybuddyapp.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.paymybuddyapp.paymybuddy.model.Transaction;
import com.paymybuddyapp.paymybuddy.model.User;
import com.paymybuddyapp.paymybuddy.repository.TransactionRepository;
import com.paymybuddyapp.paymybuddy.repository.UserRepository;
import com.paymybuddyapp.paymybuddy.service.TransactionService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DBRider
public class TransactionServiceIT {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	@DataSet(cleanBefore = true, cleanAfter = true)
	public void testSaveTransaction() {
		// GIVEN
		User sender = new User(null, "senderUser", "sender@example.com", "password");
		User receiver = new User(null, "receiverUser", "receiver@example.com", "password");
		userRepository.save(sender);
		userRepository.save(receiver);

		Transaction transaction = new Transaction();
		transaction.setSender(sender);
		transaction.setReceiver(receiver);
		transaction.setDescription("test transaction");
		transaction.setAmount(100.0);
		transaction.setDate(LocalDateTime.now());

		// WHEN
		transactionService.saveTransaction(transaction);

		// THEN
		Optional<Transaction> foundTransaction = transactionRepository.findById(transaction.getId());
		assertThat(foundTransaction).isPresent();
		assertThat(foundTransaction.get().getDescription()).isEqualTo("test transaction");
	}

	@Test
	@Transactional
	@DataSet(cleanBefore = true, cleanAfter = true)
	public void testGetTransactionsBySender() {
		// GIVEN
		User sender = new User(null, "senderUser", "sender@example.com", "password");
		User receiver = new User(null, "receiverUser", "receiver@example.com", "password");
		userRepository.save(sender);
		userRepository.save(receiver);

		Transaction transaction1 = new Transaction(null, sender, receiver, "test transaction 1", 100.0,
				LocalDateTime.now());
		Transaction transaction2 = new Transaction(null, sender, receiver, "test transaction 2", 200.0,
				LocalDateTime.now());

		transactionRepository.save(transaction1);
		transactionRepository.save(transaction2);

		// WHEN
		List<Transaction> transactions = transactionService.getTransactionsBySender(sender.getId());

		// THEN
		assertThat(transactions).hasSize(2);
	}

	@Test
	@Transactional
	@DataSet(cleanBefore = true, cleanAfter = true)
	public void testGetTransactionsByReceiver() {
		// GIVEN
		User sender = new User(null, "senderUser", "sender@example.com", "password");
		User receiver = new User(null, "receiverUser", "receiver@example.com", "password");
		userRepository.save(sender);
		userRepository.save(receiver);

		Transaction transaction1 = new Transaction(null, sender, receiver, "test transaction 1", 100.0,
				LocalDateTime.now());
		Transaction transaction2 = new Transaction(null, sender, receiver, "test transaction 2", 200.0,
				LocalDateTime.now());

		transactionRepository.save(transaction1);
		transactionRepository.save(transaction2);

		// WHEN
		List<Transaction> transactions = transactionService.getTransactionsByReceiver(receiver.getId());

		// THEN
		assertThat(transactions).hasSize(2);
	}

}
