package com.aalil.ebanking.services;

import com.aalil.ebanking.dto.AuthenticationRequest;
import com.aalil.ebanking.dto.AuthenticationResponse;
import com.aalil.ebanking.dto.UserDto;

public interface UserService extends AbstractService<UserDto>{

    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);
    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
