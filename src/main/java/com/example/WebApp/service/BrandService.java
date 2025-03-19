package com.example.WebApp.service;

import com.example.WebApp.model.Brand;
import com.example.WebApp.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;
    public List<Brand> findAll() { return brandRepository.findAll(); }
    public Brand save(Brand brand) { return brandRepository.save(brand);}
}
