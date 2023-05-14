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
public class Address extends AbstractEntity{

    private String street;
    private String country;
    private Integer postalCode;
    private String city;
    private Integer number;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
