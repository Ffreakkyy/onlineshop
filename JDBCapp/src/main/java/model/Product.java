package com.onlineshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    private int productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private int brandId;
    private LocalDateTime createdAt;

    // Конструкторы
    public Product() {
        this.createdAt = LocalDateTime.now();
    }

    public Product(String name, String description, BigDecimal price, int stockQuantity, int brandId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandId = brandId;
        this.createdAt = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId &&
                stockQuantity == product.stockQuantity &&
                brandId == product.brandId &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(price, product.price) &&
                Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, stockQuantity, brandId, createdAt);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", brandId=" + brandId +
                ", createdAt=" + createdAt +
                '}';
    }
}