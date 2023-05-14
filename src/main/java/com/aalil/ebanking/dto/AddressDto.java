package com.aalil.ebanking.dto;

import com.aalil.ebanking.models.Address;
import com.aalil.ebanking.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressDto {

    private Integer id;
    private String street;
    private String country;
    private Integer postalCode;
    private String city;
    private Integer number;
    private Integer userId;

    public static AddressDto fromEntity(Address address){
        return AddressDto.builder()
                .id(address.getId())
                .number(address.getNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .userId(address.getUser().getId())
                .build();
    }

    public static Address toEntity(AddressDto address){
        return Address.builder()
                .id(address.getId())
                .number(address.getNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .user(
                        User.builder()
                                .id(address.getId())
                                .build()
                )
                .build();
    }
}
