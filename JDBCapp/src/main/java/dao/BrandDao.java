package com.onlineshop.dao;

import com.onlineshop.model.Brand;
import com.onlineshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDao {
    // Создание нового бренда
    public int createBrand(Brand brand) throws SQLException {
        String sql = "INSERT INTO brands (name, country) VALUES (?, ?) RETURNING brand_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, brand.getName());
            stmt.setString(2, brand.getCountry());


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Creating brand failed, no ID obtained.");
        }
    }

    // Получение бренда по ID
    public Brand getBrandById(int brandId) throws SQLException {
        String sql = "SELECT * FROM brands WHERE brand_id = ?";
        Brand brand = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, brandId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                brand = new Brand();
                brand.setBrandId(rs.getInt("brand_id"));
                brand.setName(rs.getString("name"));
                brand.setCountry(rs.getString("country"));

            }
        }
        return brand;
    }

    // Обновление бренда
    public boolean updateBrand(Brand brand) throws SQLException {
        String sql = "UPDATE brands SET name = ?, country = ?, WHERE brand_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, brand.getName());
            stmt.setString(2, brand.getCountry());
            stmt.setInt(4, brand.getBrandId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Удаление бренда
    public boolean deleteBrand(int brandId) throws SQLException {
        String sql = "DELETE FROM brands WHERE brand_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, brandId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Получение всех брендов
    public List<Brand> getAllBrands() throws SQLException {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brands ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("brand_id"));
                brand.setName(rs.getString("name"));
                brand.setCountry(rs.getString("country"));


                brands.add(brand);
            }
        }
        return brands;
    }

    // Поиск брендов по стране
    public List<Brand> getBrandsByCountry(String country) throws SQLException {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brands WHERE country = ? ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, country);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("brand_id"));
                brand.setName(rs.getString("name"));
                brand.setCountry(rs.getString("country"));


                brands.add(brand);
            }
        }
        return brands;
    }

    // Проверка существования бренда по имени
    public boolean existsByName(String name) throws SQLException {
        String sql = "SELECT 1 FROM brands WHERE name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Получение количества товаров бренда
    public int getProductCount(int brandId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM products WHERE brand_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, brandId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}
