package com.example.WebApp.service;

import com.example.WebApp.dto.BrandDto;
import com.example.WebApp.exeption.DuplicateException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Brand;
import com.example.WebApp.repository.BrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return brandRepository.save(mapper.toBrand(brandDto));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateException(String.format("Brand %s already exists", brandDto.getName()));
        } catch (Exception e) {
            throw new ObjectSaveException("Error saving brand");
        }
    }

    public Brand update(BrandDto newBrand, Long id) {
        Brand oldBrand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));

        oldBrand.setName(newBrand.getName());
        oldBrand.setCountry(newBrand.getCountry());

        return brandRepository.save(oldBrand);
    }

    public void delete(Long brandId) {
        brandRepository.deleteById(brandId);
    }
}
