package com.onlineshop.model;

import java.util.Objects;

public class Brand {
    private int brandId;
    private String name;
    private String country;
    private String description; // Дополнительное поле

    // Конструкторы
    public Brand() {}

    public Brand(String name, String country) {
        this.name = name;
        this.country = country;
    }



    // Геттеры и сеттеры
    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    // equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return brandId == brand.brandId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId=" + brandId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +

                '}';
    }
}
