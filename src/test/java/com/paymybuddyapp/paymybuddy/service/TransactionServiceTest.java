package com.paymybuddyapp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddyapp.paymybuddy.model.Transaction;
import com.paymybuddyapp.paymybuddy.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionService transactionService;

	@Test
	public void testSaveTransaction() {
		// ARRANGE
		Transaction transaction = new Transaction();

		when(transactionRepository.save(transaction)).thenReturn(transaction);

		// ACT
		Transaction savedTransaction = transactionService.saveTransaction(transaction);

		// ASSERT
		assertThat(savedTransaction).isEqualTo(transaction);
	}

	@Test
	public void testGetTransactionsBySender() {
		// ARRANGE
		Long senderId = 1L;
		Transaction transaction = new Transaction();

		when(transactionRepository.findBySenderId(senderId)).thenReturn(List.of(transaction));

		// ACT
		List<Transaction> transactions = transactionService.getTransactionsBySender(senderId);

		// ASSERT
		assertThat(transactions).contains(transaction);
	}

}
