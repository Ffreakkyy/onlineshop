package com.onlineshop.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private String size;
    private String color;

    // Конструкторы
    public OrderItem() {}

    public OrderItem(int orderId, int productId, int quantity, BigDecimal price, String size, String color) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.color = color;
    }

    // Геттеры и сеттеры
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return orderId == orderItem.orderId &&
                productId == orderItem.productId &&
                Objects.equals(size, orderItem.size) &&
                Objects.equals(color, orderItem.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, size, color);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    // Дополнительные бизнес-методы
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}