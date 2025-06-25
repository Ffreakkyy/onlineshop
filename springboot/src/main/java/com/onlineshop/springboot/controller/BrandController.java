package com.onlineshop.springboot.controller;


import com.onlineshop.springboot.entity.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.onlineshop.springboot.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    // Получить все бренды
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.findAll();
    }

    // Получить бренд по ID
    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return brandService.findById(id);
    }

    // Создать новый бренд
    @PostMapping
    public Brand createBrand(@RequestBody Brand brand) {
        return brandService.save(brand);
    }

    // Обновить бренд
    @PutMapping("/{id}")
    public Brand updateBrand(@PathVariable Integer id, @RequestBody Brand brand) {
        brand.setId(id);  // Убедимся, что ID совпадает
        return brandService.save(brand);
    }

    // Удалить бренд
    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
    }

    // Найти бренд по названию (дополнительный эндпоинт)
    @GetMapping("/by-name/{name}")
    public Brand getBrandByName(@PathVariable String name) {
        return brandService.findByName(name);
    }
}