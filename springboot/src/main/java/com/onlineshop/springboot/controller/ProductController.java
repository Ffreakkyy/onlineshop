package com.onlineshop.springboot.controller;

import com.onlineshop.springboot.entity.Product;
import com.onlineshop.springboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // Получить все товары
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // Получить товар по ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    // Создать новый товар
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    // Обновить товар
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productService.save(product);
    }

    // Удалить товар
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }

    // Дополнительные эндпоинты
    @GetMapping("/by-brand/{brandId}")
    public List<Product> getProductsByBrand(@PathVariable Long brandId) {
        return productService.findByBrand(brandId);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchByName(name);
    }
}
