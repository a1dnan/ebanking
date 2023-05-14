package com.aalil.ebanking.dto;

import com.aalil.ebanking.models.Contact;
import com.aalil.ebanking.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ContactDto {

    private Integer id;
    private String email;
    @NotNull(message = "First name must not be empty")
    @NotBlank(message = "First name must not be empty")
    @NotEmpty(message = "First name must not be empty")
    private String firstName;
    @NotNull(message = "Last name must not be empty")
    @NotBlank(message = "Last name must not be empty")
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;
    @NotNull(message = "IBAN must not be empty")
    @NotBlank(message = "IBAN must not be empty")
    @NotEmpty(message = "IBAN must not be empty")
    private String iban;
    private Integer userId;

    public static ContactDto fromEntity(Contact contact){
        return ContactDto.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .userId(contact.getUser().getId())
                .build();
    }

    public static Contact toEntity(ContactDto contact){
        return Contact.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .user(
                        User.builder()
                                .id(contact.getUserId())
                                .build()
                )
                .build();
    }
}
