package com.paymybuddyapp.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddyapp.paymybuddy.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
