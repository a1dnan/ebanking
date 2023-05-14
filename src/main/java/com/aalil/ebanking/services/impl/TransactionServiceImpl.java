package com.aalil.ebanking.services.impl;

import com.aalil.ebanking.dto.TransactionDto;
import com.aalil.ebanking.models.Transaction;
import com.aalil.ebanking.models.TransactionType;
import com.aalil.ebanking.repositories.TransactionRepository;
import com.aalil.ebanking.services.TransactionService;
import com.aalil.ebanking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final ObjectsValidator<TransactionDto> validator;


    @Override
    public Integer save(TransactionDto dto) {
        validator.validate(dto);
        Transaction transaction = TransactionDto.toEntity(dto);
        BigDecimal transactionMultiplier = BigDecimal.valueOf(getTransactionMultiplier(transaction.getType()));
        BigDecimal amount = transaction.getAmount().multiply(transactionMultiplier);
        transaction.setAmount(amount);
        return repository.save(transaction).getId();
    }

    @Override
    public List<TransactionDto> findAll() {
        return repository.findAll().stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(Integer id) {
        return repository.findById(id)
                .map(TransactionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No Transaction was found with ID : "+id));
    }

    @Override
    public void delete(Integer id) {
        // todo check Transaction delete
        repository.deleteById(id);
    }

    public int getTransactionMultiplier(TransactionType type){
        return TransactionType.TRANSFERT == type ? -1 : 1;
    }

    @Override
    public List<TransactionDto> findAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId).stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }
}
