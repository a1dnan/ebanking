package com.aalil.ebanking.controllers;
import com.aalil.ebanking.dto.AccountDto;
import com.aalil.ebanking.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "account")
public class AccountController {

    private final AccountService service;

    @PostMapping("/")
    public ResponseEntity<Integer> saveAccount(@RequestBody AccountDto accountDto){
        return ResponseEntity.ok(service.save(accountDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> findAllAccounts(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<AccountDto> findAccountById(@PathVariable("account-id") Integer accountId){
        return ResponseEntity.ok(service.findById(accountId));
    }

    @DeleteMapping("/{account-id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("account-id") Integer accountId){
        service.delete(accountId);
        return ResponseEntity.accepted().build();
    }
}
