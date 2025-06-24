package com.onlineshop.dao;

import com.onlineshop.model.Product;
import com.onlineshop.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    // Создание продукта
    public int createProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, stock_quantity, brand_id) VALUES (?, ?, ?, ?, ?) RETURNING product_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getBrandId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Creating product failed, no ID obtained.");
        }
    }

    // Получение продукта по ID
    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        Product product = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setBrandId(rs.getInt("brand_id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
        }
        return product;
    }

    // Обновление продукта
    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock_quantity = ?, brand_id = ? WHERE product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getBrandId());
            stmt.setInt(6, product.getProductId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Удаление продукта
    public boolean deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Получение всех продуктов
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setBrandId(rs.getInt("brand_id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                products.add(product);
            }
        }
        return products;
    }

    // Поиск продуктов по названию
    public List<Product> searchProductsByName(String namePattern) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + namePattern + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setBrandId(rs.getInt("brand_id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                products.add(product);
            }
        }
        return products;
    }
}
