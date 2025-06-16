package com.backend.backend.services;

import com.backend.backend.dto.TransactionDTO;
import com.backend.backend.entities.Category;
import com.backend.backend.entities.Transaction;
import com.backend.backend.entities.User;
import com.backend.backend.repositories.CategoryRepository;
import com.backend.backend.repositories.TransactionRepository;
import com.backend.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Logica para crear una nueva transaccion
    public void createTransaction(TransactionDTO dto, String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();

        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .date(dto.getDate())
                .type(dto.getType())
                .isPublic(dto.isPublic())
                .user(user)
                .category(category)
                .build();

        transactionRepository.save(transaction);
    }

    // Busca al usuario por su email y devuelve todas sus transacciones
    public List<Transaction> getUserTransactions(String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
        return transactionRepository.findByUserId(user.getId());
    }

    // Devuelve las ultimas 5 transacciones del usuario
    public List<Transaction> getLast5Transactions(User user) {
        return transactionRepository.findTop5ByUserOrderByDateDesc(user);
    }

    // Devuelve las transacciones filtradas por categoria, fecha o categoria y fecha
    public List<Transaction> filterTransactions(String email, String categoryId, String dateString) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate date = null;
        if (dateString != null && !dateString.isEmpty()) {
            date = LocalDate.parse(dateString);
        }

        if (categoryId != null && date != null) {
            return transactionRepository.findByUserIdAndCategoryIdAndDate(user.getId(), categoryId, date);
        } else if (categoryId != null) {
            return transactionRepository.findByUserIdAndCategoryId(user.getId(), categoryId);
        } else if (date != null) {
            return transactionRepository.findByUserIdAndDate(user.getId(), date);
        } else {
            return transactionRepository.findByUserId(user.getId());
        }
    }
}
