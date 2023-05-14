package com.aalil.ebanking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact extends AbstractEntity{


    private String email;

    private String firstName;
    private String lastName;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
