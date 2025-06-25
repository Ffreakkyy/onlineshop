package com.onlineshop.springboot.repository;

import com.onlineshop.springboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Поиск по бренду
    List<Product> findByBrandId(Long brandId);

    // Поиск по категории
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    // Поиск по названию (регистронезависимый)
    List<Product> findByNameContainingIgnoreCase(String name);
}