package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.PasswordDto;
import com.example.WebApp.dto.UserUpdateDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.exeption.DuplicateException;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.model.Users;
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
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;


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

    public void update(UserUpdateDto newUser) {
        Long userId = jwtProvider.getCurrentUserId();
        Users oldUser = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if (newUser.getUserName() == null && newUser.getPhone() == null && newUser.getEmail() == null)
            throw new IllegalArgumentException("Not valid");

        if (newUser.getUserName() != null)
            oldUser.setUserName(newUser.getUserName());

        if (newUser.getPhone() != null)
            oldUser.setPhone(newUser.getPhone());

        if (newUser.getEmail() != null) {
            if (usersRepository.findByEmail(newUser.getEmail()).isPresent())
                throw new DuplicateException(String.format("email %s already exists", newUser.getEmail()), "edit");
            oldUser.setEmail(newUser.getEmail());
        }
        usersRepository.save(oldUser);
    }


    public void updatePassword(PasswordDto passwordDto) {
        Users user = usersRepository.findById(jwtProvider.getCurrentUserId())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        if(user.getPassword().equals(passwordEncoder.encode(passwordDto.getOldPass())))
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPass()));
        usersRepository.save(user);
    }

    public Users findById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User %s not found", userId)));
    }
}
