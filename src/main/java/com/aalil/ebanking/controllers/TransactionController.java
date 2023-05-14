package com.aalil.ebanking.controllers;

import com.aalil.ebanking.dto.TransactionDto;
import com.aalil.ebanking.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
@Tag(name = "transaction")
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/")
    public ResponseEntity<Integer> saveTransaction(@RequestBody TransactionDto transactionDto){
        return ResponseEntity.ok(service.save(transactionDto));
    }
    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> findAllTransactions(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{transaction-id}")
    public ResponseEntity<TransactionDto> findTransactionById(@PathVariable("transaction-id") Integer transactionId){
        return ResponseEntity.ok(service.findById(transactionId));
    }
    @GetMapping("users/{user-id}")
    public ResponseEntity<List<TransactionDto>> findAllTransactionsByUserId(@PathVariable("user-id") Integer userId){
        return ResponseEntity.ok(service.findAllByUserId(userId));
    }
    @DeleteMapping("/{transaction-id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("transaction-id") Integer transactionId){
        service.delete(transactionId);
        return ResponseEntity.accepted().build();
    }
}
