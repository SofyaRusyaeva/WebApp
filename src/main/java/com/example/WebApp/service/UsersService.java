package com.example.WebApp.service;

import com.example.WebApp.dto.CartDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.exeption.DuplicateException;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.CartRepository;
import com.example.WebApp.repository.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersService {

    CartService cartService;
    UsersRepository usersRepository;
    Mapper mapper;
    PasswordEncoder passwordEncoder;

    public List<Users> findAll() { return usersRepository.findAll(); }

//    public Users save(UsersDto userDto) {
//        Users user = mapper.toUsers(userDto);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        try {
//            usersRepository.save(user);
//        } catch (DataIntegrityViolationException e) {
//            throw new DuplicateException(String.format("email %s already exists", userDto. getEmail()));
//        } catch (Exception e) {
//            throw new ObjectSaveException("Error saving user");
//        }
//        cartService.save(new CartDto(BigDecimal.ZERO, user.getUserId()));
//        return user;
//    }

    public void delete(Long userId) {
        usersRepository.deleteById(userId);
    }

    public Users update(UsersDto newUser, Long id) {
        Users oldUser = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        oldUser.setUserName(newUser.getUserName());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword())); //passwordEncoder.encode(
        oldUser.setPhone(newUser.getPhone());

        return usersRepository.save(oldUser);
    }

    public Users findById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User %s not found", userId)));
    }
}
