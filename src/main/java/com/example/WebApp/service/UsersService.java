package com.example.WebApp.service;

import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.CartRepository;
import com.example.WebApp.repository.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersService {

    UsersRepository usersRepository;
    CartRepository cartRepository;
    Mapper mapper;
    PasswordEncoder passwordEncoder;

    public List<Users> findAll() { return usersRepository.findAll(); }

    public Users save(UsersDto userDto) {
        Users user = mapper.toUsers(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public void delete(Long userId) {
        usersRepository.deleteById(userId);
    }
}
