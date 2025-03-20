package com.example.WebApp.service;

import com.example.WebApp.dto.BrandDto;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Brand;
import com.example.WebApp.repository.BrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandService {

    BrandRepository brandRepository;
    Mapper mapper;

    public List<Brand> findAll() { return brandRepository.findAll(); }
    public Brand save(BrandDto brandDto) {
        Brand brand = mapper.toBrand(brandDto);
        return brandRepository.save(brand);
    }
}
