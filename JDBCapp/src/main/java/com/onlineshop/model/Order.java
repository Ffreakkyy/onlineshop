package com.onlineshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {
    private int orderId;
    private int userId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<com.onlineshop.model.OrderItem> items;

    // Конструкторы
    public Order() {
        this.createdAt = LocalDateTime.now();
        this.status = "created";
    }

    public Order(int userId, BigDecimal totalAmount) {
        this();
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    // Геттеры и сеттеры
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<com.onlineshop.model.OrderItem> getItems() {
        return items;
    }

    public void setItems(List<com.onlineshop.model.OrderItem> items) {
        this.items = items;
    }

    // equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}



