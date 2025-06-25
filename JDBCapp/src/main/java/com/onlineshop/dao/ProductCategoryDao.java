package com.onlineshop.dao;

import com.onlineshop.model.ProductCategory;
import com.onlineshop.model.Category;
import com.onlineshop.model.Product;
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

    // Добавление категории к товару (упрощённый интерфейс)
    public boolean addCategoryToProduct(int productId, int categoryId) {
        try {
            return addProductCategory(new ProductCategory(productId, categoryId));
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении категории к товару: " + e.getMessage());
            return false;
        }
    }

    // Удаление категории у товара (упрощённый интерфейс)
    public boolean removeCategoryFromProduct(int productId, int categoryId) {
        try {
            return deleteProductCategory(new ProductCategory(productId, categoryId));
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении категории у товара: " + e.getMessage());
            return false;
        }
    }

    // Получение всех категорий для товара
    public List<Category> getCategoriesForProduct(int productId) throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = """
            SELECT c.category_id, c.name, c.description
            FROM categories c
            JOIN product_category pc ON c.category_id = pc.category_id
            WHERE pc.product_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                categories.add(c);
            }
        }
        return categories;
    }

    // Получение всех товаров в категории
    public List<Product> getProductsInCategory(int categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = """
            SELECT p.product_id, p.name, p.description, p.price, p.stock_quantity, p.brand_id
            FROM products p
            JOIN product_category pc ON p.product_id = pc.product_id
            WHERE pc.category_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setStockQuantity(rs.getInt("stock_quantity"));
                p.setBrandId(rs.getInt("brand_id"));
                products.add(p);
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
