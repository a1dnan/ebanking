package com.aalil.ebanking.services.impl;

import com.aalil.ebanking.dto.ContactDto;
import com.aalil.ebanking.models.Contact;
import com.aalil.ebanking.repositories.ContactRepository;
import com.aalil.ebanking.services.ContactService;
import com.aalil.ebanking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;
    private final ObjectsValidator<ContactDto> validator;

    @Override
    public Integer save(ContactDto dto) {
        validator.validate(dto);
        Contact contact = ContactDto.toEntity(dto);
        return repository.save(contact).getId();
    }

    @Override
    public List<ContactDto> findAll() {
        return repository.findAll().stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto findById(Integer id) {
        return repository.findById(id)
                .map(ContactDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No Contact was found with ID : "+id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete Contact
        repository.deleteById(id);
    }

    @Override
    public List<ContactDto> findAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId).stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }
}
