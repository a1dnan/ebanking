package com.aalil.ebanking.services.impl;

import com.aalil.ebanking.dto.AccountDto;
import com.aalil.ebanking.exceptions.OperationNoPermittedException;
import com.aalil.ebanking.models.Account;
import com.aalil.ebanking.repositories.AccountRepository;
import com.aalil.ebanking.services.AccountService;
import com.aalil.ebanking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ObjectsValidator<AccountDto> validator;

    @Override
    public Integer save(AccountDto dto) {
        // block account update
       /* if (dto.getId() != null){
            throw new OperationNoPermittedException(
                    "Account cannot be updated",
                    "save account",
                    "Account",
                    "Update not permitted"
            );
        }*/
        validator.validate(dto);
        Account account = AccountDto.toEntity(dto);
//        boolean userHasAlreadyAnAccount = repository.findByUserId(account.getUser().getId()).isPresent();
//        if (userHasAlreadyAnAccount && account.getUser().isActive()){
//            throw new OperationNoPermittedException(
//                    "User has already an active account",
//                    "Create account",
//                    "Account service",
//                    "Account Creation"
//            );
//        }
        //generate Iban
        if (dto.getId() == null){
            account.setIban(generateRandomIban());
        }
        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AccountDto::fromEntity)//account -> AccountDto.fromEntity(account)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {
        return repository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No Account was found with ID : "+id));
    }

    @Override
    public void delete(Integer id) {
        // todo check account delete
        repository.deleteById(id);
    }

    public String generateRandomIban(){
        // generate random Iban
        String iban = Iban.random(CountryCode.DE).toFormattedString();
        // Check if Iban already exists
        boolean ibanExists = repository.findByIban(iban).isPresent();
        // Iban generated already exists
        if (ibanExists)
            generateRandomIban();
        // Iban generated not exists
        return iban;
    }
}
