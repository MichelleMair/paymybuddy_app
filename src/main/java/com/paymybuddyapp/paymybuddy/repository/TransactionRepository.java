package com.paymybuddyapp.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddyapp.paymybuddy.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findBySenderId(Long senderId);

	List<Transaction> findByReceiverId(Long receiverId);

}
