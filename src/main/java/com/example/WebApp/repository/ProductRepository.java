package com.example.WebApp.repository;

import com.example.WebApp.model.Product;
import com.example.WebApp.model.ProductCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIn(List<ProductCategory> categories, Sort sort);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Sort sort);

    List<Product> findByBrand_NameIn(List<String> brandNames, Sort sort);

    List<Product> findByCategoryInAndPriceBetween(List<ProductCategory> categories,BigDecimal minPrice, BigDecimal maxPrice, Sort sort);

    List<Product> findByCategoryInAndBrand_NameIn(List<ProductCategory> categories, List<String> brandNames, Sort sort);

    List<Product> findByPriceBetweenAndBrand_NameIn(BigDecimal minPrice, BigDecimal maxPrice, List<String> brandNames, Sort sort);

    List<Product> findByCategoryInAndPriceBetweenAndBrand_NameIn(List<ProductCategory> categories, BigDecimal minPrice, BigDecimal maxPrice, List<String> brandNames, Sort sort);

//    List<Product> findAll(Sort sort);
}
