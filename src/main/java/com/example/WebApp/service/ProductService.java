package com.example.WebApp.service;

import com.example.WebApp.dto.ProductDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Brand;
import com.example.WebApp.model.Product;
import com.example.WebApp.repository.BrandRepository;
import com.example.WebApp.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    BrandRepository brandRepository;
    Mapper mapper;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Product %s not found", productId)));
    }

    public Product save(ProductDto productDto) {
        Brand brand = brandRepository.findById(productDto.getBrandId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Product %s not found", productDto.getBrandId())));
        Product product = mapper.toProduct(productDto, brand);
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new ObjectSaveException("Error saving brand");
        }
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product update(ProductDto newProduct, Long id) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Product %s not found", id)));

        Brand brand = brandRepository.findById(newProduct.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + newProduct.getBrandId()));

        oldProduct.setName(newProduct.getName());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setBrand(brand);
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setCategory(newProduct.getCategory());

        return productRepository.save(oldProduct);
    }

    public List<Product> findAllSorted(String sortBy, String sortDirection) {
        if ("desc".equalsIgnoreCase(sortDirection) && "name".equalsIgnoreCase(sortBy)) {
            return productRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        }
        if ("asc".equalsIgnoreCase(sortDirection) && "name".equalsIgnoreCase(sortBy)) {
            return productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        }
        if ("desc".equalsIgnoreCase(sortDirection) && "price".equalsIgnoreCase(sortBy)) {
            return productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        }
        if ("asc".equalsIgnoreCase(sortDirection) && "price".equalsIgnoreCase(sortBy)) {
            return productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
        }
        return productRepository.findAll();
    }
}
