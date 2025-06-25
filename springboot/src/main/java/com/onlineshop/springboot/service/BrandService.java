package com.onlineshop.springboot.service;

import com.onlineshop.springboot.entity.Brand;
import com.onlineshop.springboot.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    // Получить все бренды
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    // Найти бренд по ID
    public Brand findById(Integer id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
    }

    // Создать/обновить бренд
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    // Удалить бренд
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }

    // Найти бренд по названию (дополнительный метод)
    public Brand findByName(String name) {
        return brandRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Brand not found with name: " + name));
    }

    public Brand findById(Long id) {
        return null;
    }
}