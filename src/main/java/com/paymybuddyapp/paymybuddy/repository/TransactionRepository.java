package com.paymybuddyapp.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddyapp.paymybuddy.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
