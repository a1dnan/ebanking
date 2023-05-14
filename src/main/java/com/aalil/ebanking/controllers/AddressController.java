package com.aalil.ebanking.controllers;

import com.aalil.ebanking.dto.AddressDto;
import com.aalil.ebanking.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("addresses")
@Tag(name = "address")
public class AddressController {

    private final AddressService service;

    @PostMapping("/")
    public ResponseEntity<Integer> saveAddress(@RequestBody AddressDto addressDto){
        return ResponseEntity.ok(service.save(addressDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> findAllAddresses(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{address-id}")
    public ResponseEntity<AddressDto> findAddressById(@PathVariable("address-id") Integer addressId){
        return ResponseEntity.ok(service.findById(addressId));
    }

    @DeleteMapping("/{address-id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("address-id") Integer addressId){
        service.delete(addressId);
        return ResponseEntity.accepted().build();
    }
}
