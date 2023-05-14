package com.aalil.ebanking.services.impl;

import com.aalil.ebanking.dto.AddressDto;
import com.aalil.ebanking.models.Address;
import com.aalil.ebanking.repositories.AddressRepository;
import com.aalil.ebanking.services.AddressService;
import com.aalil.ebanking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final ObjectsValidator<AddressDto> validator;

    @Override
    public Integer save(AddressDto dto) {
        validator.validate(dto);
        Address address = AddressDto.toEntity(dto);
        return repository.save(address).getId();
    }

    @Override
    public List<AddressDto> findAll() {
        return repository.findAll().stream()
                .map(AddressDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto findById(Integer id) {
        return repository.findById(id)
                .map(AddressDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No Address was found with ID : "+id))
                ;
    }

    @Override
    public void delete(Integer id) {
        // todo check delete Address
        repository.deleteById(id);
    }
}
