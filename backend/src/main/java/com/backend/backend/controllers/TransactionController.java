package com.backend.backend.controllers;


import com.backend.backend.dto.TransactionDTO;
import com.backend.backend.entities.Transaction;
import com.backend.backend.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Maneja peticion para crear una transaccion
    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionDTO dto, Principal principal) {
        try {
            transactionService.createTransaction(dto, principal.getName());
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    // Devuelve las transacciones del usuario que esta logueado 
    @GetMapping
    public ResponseEntity<List<Transaction>> getMyTransactions(Principal principal) {
        try {
            List<Transaction> transactions= transactionService.getUserTransactions(principal.getName());
            return ResponseEntity.ok(transactions);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
