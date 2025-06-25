package com.onlineshop.dao;

import com.onlineshop.model.OrderItem;
import com.onlineshop.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {

    // Добавление позиции в заказ
    public boolean addOrderItem(OrderItem item) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price, size, color) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setBigDecimal(4, item.getPrice());
            stmt.setString(5, item.getSize());
            stmt.setString(6, item.getColor());

            return stmt.executeUpdate() > 0;
        }
    }

    // Получение всех позиций для заказа
    public List<OrderItem> getItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setSize(rs.getString("size"));
                item.setColor(rs.getString("color"));

                items.add(item);
            }
        }
        return items;
    }

    // Обновление количества товара в позиции
    public boolean updateOrderItemQuantity(OrderItem item) throws SQLException {
        String sql = "UPDATE order_items SET quantity = ? " +
                "WHERE order_id = ? AND product_id = ? AND size = ? AND color = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getQuantity());
            stmt.setInt(2, item.getOrderId());
            stmt.setInt(3, item.getProductId());
            stmt.setString(4, item.getSize());
            stmt.setString(5, item.getColor());

            return stmt.executeUpdate() > 0;
        }
    }

    // Удаление позиции из заказа
    public boolean removeOrderItem(OrderItem item) throws SQLException {
        String sql = "DELETE FROM order_items " +
                "WHERE order_id = ? AND product_id = ? AND size = ? AND color = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setString(3, item.getSize());
            stmt.setString(4, item.getColor());

            return stmt.executeUpdate() > 0;
        }
    }

    // Проверка существования позиции в заказе
    public boolean exists(OrderItem item) throws SQLException {
        String sql = "SELECT 1 FROM order_items " +
                "WHERE order_id = ? AND product_id = ? AND size = ? AND color = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setString(3, item.getSize());
            stmt.setString(4, item.getColor());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Получение общей суммы по заказу
    public BigDecimal calculateOrderTotal(int orderId) throws SQLException {
        String sql = "SELECT SUM(price * quantity) AS total FROM order_items WHERE order_id = ?";
        BigDecimal total = BigDecimal.ZERO;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getBigDecimal("total");
            }
        }
        return total != null ? total : BigDecimal.ZERO;
    }
}