package com.example.WebApp.service;

import com.example.WebApp.model.Users;
import com.example.WebApp.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    public List<Users> findAll() { return usersRepository.findAll(); }
    public Users save(Users users) { return usersRepository.save(users); }
}
