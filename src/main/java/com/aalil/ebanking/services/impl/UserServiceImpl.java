package com.aalil.ebanking.services.impl;

import com.aalil.ebanking.config.JwtUtils;
import com.aalil.ebanking.dto.AccountDto;
import com.aalil.ebanking.dto.AuthenticationRequest;
import com.aalil.ebanking.dto.AuthenticationResponse;
import com.aalil.ebanking.dto.UserDto;
import com.aalil.ebanking.exceptions.OperationNoPermittedException;
import com.aalil.ebanking.models.Account;
import com.aalil.ebanking.models.Role;
import com.aalil.ebanking.models.User;
import com.aalil.ebanking.repositories.AccountRepository;
import com.aalil.ebanking.repositories.RoleRepository;
import com.aalil.ebanking.repositories.UserRepository;
import com.aalil.ebanking.services.AccountService;
import com.aalil.ebanking.services.UserService;
import com.aalil.ebanking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository repository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final ObjectsValidator<UserDto> validator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;

    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user).getId();
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDto::fromEntity) //user -> UserDto.fromEntity(user)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return repository.findById(id)
                .map(UserDto::fromEntity) //user -> UserDto.fromEntity(user)
                .orElseThrow(() -> new EntityNotFoundException("No User was found with ID : "+id));
    }

    @Override
    public void delete(Integer id) {
        // Todo Check before delete
        repository.deleteById(id);
    }

    @Override
    public Integer validateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No User was found for account validation"));
        boolean hasAlreadyAnAccount = accountRepository.findByUserId(id).isPresent();
        if (!hasAlreadyAnAccount) {
            AccountDto account = AccountDto.builder()
                    .user(UserDto.fromEntity(user))
                    .build();
            var acc = accountService.save(account);
            user.setAccount(
                    Account.builder()
                            .id(acc)
                            .build()
            );
        }
        if (hasAlreadyAnAccount && user.isActive()){
            throw new OperationNoPermittedException(
                    "User has already an active account",
                    "Create account",
                    "Account service",
                    "Account Creation"
            );
        }
        user.setActive(true);
        repository.save(user);
        return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No User was found for account validation"));
        user.setActive(false);
        repository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(
                findOrCreateRole(ROLE_USER)
        );
        var savedUser = repository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstName() + " " + savedUser.getLastName());
        String token = jwtUtils.generateToken(savedUser,claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final User user = repository.findByEmail(request.getEmail()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("name", user.getFirstName()+" "+user.getLastName());
        String token = jwtUtils.generateToken(user,claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private Role findOrCreateRole(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElse(null);
        if (role == null) {
            return roleRepository.save(
                    Role.builder()
                            .name(roleName)
                            .build()
            );
        }
        return role;
    }
}
