package com.onlineshop.dao;

import com.onlineshop.model.ProductCategory;
import com.onlineshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDao {

    // Добавление связи товар-категория
    public boolean addProductCategory(ProductCategory productCategory) throws SQLException {
        String sql = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productCategory.getProductId());
            stmt.setInt(2, productCategory.getCategoryId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Удаление связи товар-категория
    public boolean deleteProductCategory(ProductCategory productCategory) throws SQLException {
        String sql = "DELETE FROM product_category WHERE product_id = ? AND category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productCategory.getProductId());
            stmt.setInt(2, productCategory.getCategoryId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Получение всех категорий для товара
    public List<Integer> getCategoriesForProduct(int productId) throws SQLException {
        List<Integer> categories = new ArrayList<>();
        String sql = "SELECT category_id FROM product_category WHERE product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(rs.getInt("category_id"));
            }
        }
        return categories;
    }

    // Получение всех товаров в категории
    public List<Integer> getProductsInCategory(int categoryId) throws SQLException {
        List<Integer> products = new ArrayList<>();
        String sql = "SELECT product_id FROM product_category WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(rs.getInt("product_id"));
            }
        }
        return products;
    }

    // Проверка существования связи
    public boolean exists(ProductCategory productCategory) throws SQLException {
        String sql = "SELECT 1 FROM product_category WHERE product_id = ? AND category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productCategory.getProductId());
            stmt.setInt(2, productCategory.getCategoryId());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
