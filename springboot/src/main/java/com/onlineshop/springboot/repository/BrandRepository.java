package com.onlineshop.springboot.repository;

import com.onlineshop.springboot.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByName(String name);  // Для поиска по названию бренда

    void deleteById(Long id);
}
