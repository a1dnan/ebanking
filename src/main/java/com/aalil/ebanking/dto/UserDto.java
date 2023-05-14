package com.aalil.ebanking.dto;

import com.aalil.ebanking.models.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotNull(message = "First name must not be empty")
    @NotBlank(message = "First name must not be empty")
    @NotEmpty(message = "First name must not be empty")
    private String firstName;
    @NotNull(message = "Last name must not be empty")
    @NotBlank(message = "Last name must not be empty")
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;
    @NotNull(message = "Email must not be empty")
    @NotBlank(message = "Email must not be empty")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email invalid")
    private String email;
    @NotNull(message = "Password must not be empty")
    @NotBlank(message = "Password must not be empty")
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String password;

    private String iban;
    private boolean active;

    public static UserDto fromEntity(User user){

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .iban(user.getAccount() == null ? "" : user.getAccount().getIban())
                .active(user.isActive())
                .build();
    }

    public static User toEntity(UserDto user){

        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
