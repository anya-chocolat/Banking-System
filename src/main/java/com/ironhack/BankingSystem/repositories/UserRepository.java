package com.ironhack.BankingSystem.repositories;

import com.ironhack.BankingSystem.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
