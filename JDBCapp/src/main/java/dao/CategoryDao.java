package com.onlineshop.dao;

import com.onlineshop.model.Category;
import com.onlineshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    // Создание новой категории
    public int createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?) RETURNING category_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Creating category failed, no ID obtained.");
        }
    }

    // Получение категории по ID
    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        Category category = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
            }
        }
        return category;
    }

    // Обновление категории
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getCategoryId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Удаление категории
    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM categories WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Получение всех категорий
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));

                categories.add(category);
            }
        }
        return categories;
    }

    // Проверка существования категории по имени
    public boolean existsByName(String name) throws SQLException {
        String sql = "SELECT 1 FROM categories WHERE name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Получение категории по имени
    public Category getCategoryByName(String name) throws SQLException {
        String sql = "SELECT * FROM categories WHERE name = ?";
        Category category = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
            }
        }
        return category;
    }

    // Получение количества товаров в категории
    public int getProductCount(int categoryId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product_category WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}
