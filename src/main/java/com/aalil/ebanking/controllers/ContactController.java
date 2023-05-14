package com.aalil.ebanking.controllers;

import com.aalil.ebanking.dto.ContactDto;
import com.aalil.ebanking.services.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("contacts")
@Tag(name = "contact")
public class ContactController {

    private final ContactService service;

    @PostMapping("/")
    public ResponseEntity<Integer> saveContact(@RequestBody ContactDto contactDto){
        return ResponseEntity.ok(service.save(contactDto));
    }
    @GetMapping("/")
    public ResponseEntity<List<ContactDto>> findAllContacts(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{contact-id}")
    public ResponseEntity<ContactDto>findContactById(@PathVariable("contact-id") Integer contactId){
        return ResponseEntity.ok(service.findById(contactId));
    }
    @GetMapping("/users/{user-id}")
    public ResponseEntity<List<ContactDto>> findAllContactsByUserId(@PathVariable("user-id") Integer userId){
        return ResponseEntity.ok(service.findAllByUserId(userId));
    }
    @DeleteMapping("/{contact-id}")
    public ResponseEntity<Void> deleteContact(@PathVariable("contact-id") Integer contactId){
        service.delete(contactId);
        return ResponseEntity.accepted().build();
    }
}
