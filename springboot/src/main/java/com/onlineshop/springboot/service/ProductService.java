package com.onlineshop.springboot.service;

import com.onlineshop.springboot.entity.Product;
import com.onlineshop.springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;

    // Получить все товары
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Найти товар по ID
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Создать/обновить товар
    public Product save(Product product) {
        // Проверяем существование бренда
        brandService.findById(product.getBrand().getId());
        return productRepository.save(product);
    }

    // Удалить товар
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // Найти товары по бренду
    public List<Product> findByBrand(Long brandId) {
        return productRepository.findByBrandId(brandId);
    }

    // Найти товары по категории
    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Поиск по названию
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}