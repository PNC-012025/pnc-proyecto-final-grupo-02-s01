package com.backend.backend.repositories;

import com.backend.backend.entities.Transaction;
import com.backend.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);
    List<Transaction> findTop5ByUserOrderByDateDesc(User user);
    List<Transaction> findByUser(User user);
    List<Transaction> findByIsPublicTrue();
}
