package com.onlineshop;

import com.onlineshop.dao.BrandDao;
import com.onlineshop.dao.CategoryDao;
import com.onlineshop.dao.OrderDao;
import com.onlineshop.dao.OrderItemDao;
import com.onlineshop.dao.ProductCategoryDao;
import com.onlineshop.dao.ProductDao;
import com.onlineshop.dao.UserDao;
import com.onlineshop.model.Brand;
import com.onlineshop.model.Category;
import com.onlineshop.model.Order;
import com.onlineshop.model.OrderItem;
import com.onlineshop.model.Product;
import com.onlineshop.model.ProductCategory;
import com.onlineshop.model.User;
import com.onlineshop.util.DatabaseConnection;
import com.onlineshop.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDao userDao = new UserDao();
    private static final ProductDao productDao = new ProductDao();
    private static final CategoryDao categoryDao = new CategoryDao();
    private static final BrandDao brandDao = new BrandDao();
    private static final OrderDao orderDao = new OrderDao();
    private static final OrderItemDao orderItemDao = new OrderItemDao();
    private static final ProductCategoryDao productCategoryDao = new ProductCategoryDao();

    public static void main(String[] args) {
        if (initializeDatabase()) {
            showMainMenu();
        }
    }

    private static boolean initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✅ Соединение с базой данных установлено");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Ошибка подключения к базе данных: " + e.getMessage());
            return false;
        }
    }

    private static int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    private static BigDecimal readBigDecimalInput() {
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное значение: ");
            }
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Пользователи");
            System.out.println("2. Товары");
            System.out.println("3. Категории");
            System.out.println("4. Бренды");
            System.out.println("5. Заказы");
            System.out.println("6. продукты");
            System.out.println("7. Категороии продуктов");
            System.out.println("0. Выход");
            System.out.print("Выберите: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> manageUsers();
                case 2 -> manageProducts();
                case 3 -> manageCategories();
                case 4 -> manageBrands();
                case 5 -> manageOrders();
                case 6 -> manageOrdersItems();
                case 7 -> manageProductsCategories();
                case 0 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void manageUsers() {
        while (true) {
            System.out.println("=== Управление пользователями ===");
            System.out.println("1. Добавить пользователя");
            System.out.println("2. Просмотреть всех пользователей");
            System.out.println("3. Найти пользователя по ID");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> listAllUsers();
                case 3 -> getUserById();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
    private static void addUser() {
        System.out.println(" Добавление нового пользователя:");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();
        System.out.print("Полное имя: ");
        String fullName = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();

        User user = new User(email, password, fullName, phone);
        userDao.addUser(user);
        System.out.println("✅ Пользователь успешно добавлен с ID: " + user.getUserId());
    }

    private static void listAllUsers() {
        List<User> users = userDao.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
            return;
        }

        System.out.println(" Список всех пользователей:");
        System.out.printf("%-5s %-25s %-20s %-15s%n", "ID", "Email", "Имя", "Телефон");
        users.forEach(u -> System.out.printf("%-5d %-25s %-20s %-15s%n",
                u.getUserId(), u.getEmail(), u.getFullName(), u.getPhone()));
    }
    private static void getUserById() {
        System.out.print(" Введите ID пользователя: ");
        int userId = readIntInput();

        User user = userDao.getUserById(userId);
        if (user != null) {
            System.out.println(" === Информация о пользователе ===");
                    System.out.println("ID: " + user.getUserId());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Полное имя: " + user.getFullName());
            System.out.println("Телефон: " + user.getPhone());
            System.out.println("Дата регистрации: " + user.getRegistrationDate());
        } else {
            System.out.println("❌ Пользователь с ID " + userId + " не найден");
        }
    }

    private static void updateUser() {
        System.out.print(" Введите ID пользователя для обновления: ");
        int userId = readIntInput();

        User user = userDao.getUserById(userId);
        if (user == null) {
            System.out.println("❌ Пользователь с ID " + userId + " не найден");
            return;
        }

        System.out.println("Текущие данные пользователя:");
        System.out.println("1. Email: " + user.getEmail());
        System.out.println("2. Пароль: [скрыт]");
        System.out.println("3. Полное имя: " + user.getFullName());
        System.out.println("4. Телефон: " + user.getPhone());
        System.out.print(" Введите номера полей для изменения (через запятую): ");

        String[] fieldsToUpdate = scanner.nextLine().split(",");
        for (String field : fieldsToUpdate) {
            switch (field.trim()) {
                case "1" -> {
                    System.out.print("Новый email: ");
                    user.setEmail(scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Новый пароль: ");
                    user.setPasswordHash(scanner.nextLine());
                }
                case "3" -> {
                    System.out.print("Новое полное имя: ");
                    user.setFullName(scanner.nextLine());
                }
                case "4" -> {
                    System.out.print("Новый телефон: ");
                    user.setPhone(scanner.nextLine());
                }
            }
        }

        if (userDao.updateUser(user)) {
            System.out.println("✅ Данные пользователя успешно обновлены");
        } else {
            System.out.println("❌ Не удалось обновить данные пользователя");
        }
    }
    private static void deleteUser() {
        System.out.print(" Введите ID пользователя для удаления: ");
        int userId = readIntInput();

        User user = userDao.getUserById(userId);
        if (user == null) {
            System.out.println("❌ Пользователь с ID " + userId + " не найден");
            return;
        }

        System.out.println(" Вы действительно хотите удалить пользователя:");
        System.out.println("ID: " + user.getUserId());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Полное имя: " + user.getFullName());
        System.out.print("Подтвердите удаление (y/n): ");

        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            if (userDao.deleteUser(userId)) {
                System.out.println("✅ Пользователь успешно удален");
            } else {
                System.out.println("❌ Не удалось удалить пользователя");
            }
        } else {
            System.out.println("Удаление отменено");
        }
    }
    private static void manageProducts() {
        while (true) {
            System.out.println("\n=== Управление товарами ===");
            System.out.println("1. Добавить товар");
            System.out.println("2. Показать все товары");
            System.out.println("3. Найти товар по ID");
            System.out.println("4. Обновить товар");
            System.out.println("5. Удалить товар");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> listAllProducts();
                case 3 -> getProductById();
                case 4 -> updateProduct();
                case 5 -> deleteProduct();
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
    private static void addProduct() {
        System.out.println(" Добавление нового товара:");
        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Описание: ");
        String description = scanner.nextLine();
        System.out.print("Цена: ");
        BigDecimal price = readBigDecimalInput();
        System.out.print("Количество на складе: ");
        int stock = readIntInput();
        System.out.print("ID бренда: ");
        int brandId = readIntInput();

        Product product = new Product(name, description, price, stock, brandId);
        try {
            productDao.createProduct(product);
            System.out.println("✅ Товар успешно добавлен с ID: " + product.getProductId());
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    private static void listAllProducts() {
        try {
            List<Product> products = productDao.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("Список товаров пуст.");
                return;
            }

            System.out.println(" Список всех товаров:");
            System.out.printf("%-5s %-25s %-10s %-10s %-10s%n", "ID", "Название", "Цена", "Склад", "БрендID");
            for (Product p : products) {
                System.out.printf("%-5d %-25s %-10s %-10d %-10d%n",
                        p.getProductId(), p.getName(), p.getPrice(), p.getStockQuantity(), p.getBrandId());
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка товаров: " + e.getMessage());
        }
    }

    private static void getProductById() {
        System.out.print(" Введите ID товара: ");
        int productId = readIntInput();

        try {
            Product product = productDao.getProductById(productId);
            if (product != null) {
                System.out.println(" Информация о товаре:");
                System.out.println("ID: " + product.getProductId());
                System.out.println("Название: " + product.getName());
                System.out.println("Описание: " + product.getDescription());
                System.out.println("Цена: " + product.getPrice());
                System.out.println("Количество: " + product.getStockQuantity());
                System.out.println("Бренд ID: " + product.getBrandId());
            } else {
                System.out.println("❌ Товар с ID " + productId + " не найден");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении товара: " + e.getMessage());
        }
    }
    private static void updateProduct() {
        System.out.print(" Введите ID товара для обновления: ");
        int productId = readIntInput();

        try {
            Product product = productDao.getProductById(productId);
            if (product == null) {
                System.out.println("❌ Товар с ID " + productId + " не найден");
                return;
            }

            System.out.print("Новое название (текущее: " + product.getName() + "): ");
            product.setName(scanner.nextLine());
            System.out.print("Новое описание: ");
            product.setDescription(scanner.nextLine());
            System.out.print("Новая цена: ");
            product.setPrice(readBigDecimalInput());
            System.out.print("Новое количество: ");
            product.setStockQuantity(readIntInput());
            System.out.print("Новый ID бренда: ");
            product.setBrandId(readIntInput());

            if (productDao.updateProduct(product)) {
                System.out.println("✅ Товар успешно обновлён");
            } else {
                System.out.println("❌ Не удалось обновить товар");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при обновлении товара: " + e.getMessage());
        }
    }

    private static void deleteProduct() {
        System.out.print(" Введите ID товара для удаления: ");
        int productId = readIntInput();

        try {
            Product product = productDao.getProductById(productId);
            if (product == null) {
                System.out.println("❌ Товар с ID " + productId + " не найден");
                return;
            }

            System.out.print("Вы уверены, что хотите удалить товар '" + product.getName() + "'? (y/n): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                if (productDao.deleteProduct(productId)) {
                    System.out.println("✅ Товар успешно удалён");
                } else {
                    System.out.println("❌ Не удалось удалить товар");
                }
            } else {
                System.out.println("Удаление отменено.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении товара: " + e.getMessage());
        }
    }

    private static void manageCategories() {
        while (true) {
            System.out.println(" === Управление категориями ===");
            System.out.println("1. Добавить категорию");
            System.out.println("2. Показать все категории");
            System.out.println("3. Найти категорию по ID");
            System.out.println("4. Обновить категорию");
            System.out.println("5. Удалить категорию");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> addCategory();
                case 2 -> listAllCategories();
                case 3 -> getCategoryById();
                case 4 -> updateCategory();
                case 5 -> deleteCategory();
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
    private static void addCategory() {
        System.out.println(" Добавление новой категории:");
        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Описание: ");
        String description = scanner.nextLine();

        Category category = new Category(name, description);
        try {
            categoryDao.createCategory(category);
            System.out.println("✅ Категория успешно добавлена с ID: " + category.getCategoryId());
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении категории: " + e.getMessage());
        }
    }

    private static void listAllCategories() {
        try {
            List<Category> categories = categoryDao.getAllCategories();
            if (categories.isEmpty()) {
                System.out.println("Список категорий пуст.");
                return;
            }

            System.out.println(" Список всех категорий:");
            System.out.printf("%-5s %-20s %-40s%n", "ID", "Название", "Описание");
            for (Category c : categories) {
                System.out.printf("%-5d %-20s %-40s%n", c.getCategoryId(), c.getName(), c.getDescription());
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении категорий: " + e.getMessage());
        }
    }

    private static void getCategoryById() {
        System.out.print(" Введите ID категории: ");
        int categoryId = readIntInput();

        try {
            Category category = categoryDao.getCategoryById(categoryId);
            if (category != null) {
                System.out.println(" Информация о категории:");
                System.out.println("ID: " + category.getCategoryId());
                System.out.println("Название: " + category.getName());
                System.out.println("Описание: " + category.getDescription());
            } else {
                System.out.println("❌ Категория с ID " + categoryId + " не найдена");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении категории: " + e.getMessage());
        }
    }
    private static void updateCategory() {
        System.out.print(" Введите ID категории для обновления: ");
        int categoryId = readIntInput();

        try {
            Category category = categoryDao.getCategoryById(categoryId);
            if (category == null) {
                System.out.println("❌ Категория с ID " + categoryId + " не найдена");
                return;
            }

            System.out.print("Новое название (текущее: " + category.getName() + "): ");
            category.setName(scanner.nextLine());
            System.out.print("Новое описание: ");
            category.setDescription(scanner.nextLine());

            if (categoryDao.updateCategory(category)) {
                System.out.println("✅ Категория успешно обновлена");
            } else {
                System.out.println("❌ Не удалось обновить категорию");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при обновлении категории: " + e.getMessage());
        }
    }

    private static void deleteCategory() {
        System.out.print(" Введите ID категории для удаления: ");
        int categoryId = readIntInput();

        try {
            Category category = categoryDao.getCategoryById(categoryId);
            if (category == null) {
                System.out.println("❌ Категория с ID " + categoryId + " не найдена");
                return;
            }

            System.out.print("Вы уверены, что хотите удалить категорию '" + category.getName() + "'? (y/n): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                if (categoryDao.deleteCategory(categoryId)) {
                    System.out.println("✅ Категория успешно удалена");
                } else {
                    System.out.println("❌ Не удалось удалить категорию");
                }
            } else {
                System.out.println("Удаление отменено.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении категории: " + e.getMessage());
        }
    }

    private static void manageBrands() {
        while (true) {
            System.out.println(" === Управление брендами ===");
            System.out.println("1. Добавить бренд");
            System.out.println("2. Показать все бренды");
            System.out.println("3. Найти бренд по ID");
            System.out.println("4. Обновить бренд");
            System.out.println("5. Удалить бренд");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> addBrand();
                case 2 -> listAllBrands();
                case 3 -> getBrandById();
                case 4 -> updateBrand();
                case 5 -> deleteBrand();
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
    private static void addBrand() {
        System.out.println(" Добавление нового бренда:");
        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Страна: ");
        String country = scanner.nextLine();


        Brand brand = new Brand(name, country);
        try {
            brandDao.createBrand(brand);
            System.out.println("✅ Бренд успешно добавлен с ID: " + brand.getBrandId());
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении бренда: " + e.getMessage());
        }
    }

    private static void listAllBrands() {
        try {
            List<Brand> brands = brandDao.getAllBrands();
            if (brands.isEmpty()) {
                System.out.println("Список брендов пуст.");
                return;
            }

            System.out.println(" Список всех брендов:");
            System.out.printf("%-5s %-20s %-15s %-30s%n", "ID", "Название", "Страна", "Описание");
            for (Brand b : brands) {
                System.out.printf("%-5d %-20s %-15s %-30s%n", b.getBrandId(), b.getName(), b.getCountry() );
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка брендов: " + e.getMessage());
        }
    }

    private static void getBrandById() {
        System.out.print(" Введите ID бренда: ");
        int brandId = readIntInput();

        try {
            Brand brand = brandDao.getBrandById(brandId);
            if (brand != null) {
                System.out.println(" Информация о бренде:");
                System.out.println("ID: " + brand.getBrandId());
                System.out.println("Название: " + brand.getName());
                System.out.println("Страна: " + brand.getCountry());

            } else {
                System.out.println("❌ Бренд с ID " + brandId + " не найден");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении бренда: " + e.getMessage());
        }
    }
    private static void updateBrand() {
        System.out.print(" Введите ID бренда для обновления: ");
        int brandId = readIntInput();

        try {
            Brand brand = brandDao.getBrandById(brandId);
            if (brand == null) {
                System.out.println("❌ Бренд с ID " + brandId + " не найден");
                return;
            }

            System.out.print("Новое название (текущее: " + brand.getName() + "): ");
            brand.setName(scanner.nextLine());
            System.out.print("Новая страна: ");
            brand.setCountry(scanner.nextLine());


            if (brandDao.updateBrand(brand)) {
                System.out.println("✅ Бренд успешно обновлён");
            } else {
                System.out.println("❌ Не удалось обновить бренд");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при обновлении бренда: " + e.getMessage());
        }
    }

    private static void deleteBrand() {
        System.out.print(" Введите ID бренда для удаления: ");
        int brandId = readIntInput();

        try {
            Brand brand = brandDao.getBrandById(brandId);
            if (brand == null) {
                System.out.println("❌ Бренд с ID " + brandId + " не найден");
                return;
            }

            System.out.print("Вы уверены, что хотите удалить бренд '" + brand.getName() + "'? (y/n): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                if (brandDao.deleteBrand(brandId)) {
                    System.out.println("✅ Бренд успешно удалён");
                } else {
                    System.out.println("❌ Не удалось удалить бренд");
                }
            } else {
                System.out.println("Удаление отменено.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении бренда: " + e.getMessage());
        }
    }
    private static void manageOrders() {
        while (true) {
            System.out.println("\n=== Управление заказами ===");
            System.out.println("1. Создать заказ");
            System.out.println("2. Показать все заказы");
            System.out.println("3. Найти заказ по ID");
            System.out.println("4. Удалить заказ");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> createOrder();
                case 2 -> listAllOrders();
                case 3 -> getOrderById();
                case 4 -> deleteOrder();
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
    private static void createOrder() {
        System.out.print("\nВведите ID пользователя, который делает заказ: ");
        int userId = readIntInput();

        try {
            Order order = new Order(userId, BigDecimal.ZERO); // Статус установим позже вручную
            order.setStatus("Создан");
            orderDao.createOrder(order);
            int orderId = order.getOrderId();

            System.out.println("\nДобавьте товары в заказ. Введите 0 для завершения.");
            while (true) {
                System.out.print("ID товара: ");
                int productId = readIntInput();
                if (productId == 0) break;

                Product product = productDao.getProductById(productId);
                if (product == null) {
                    System.out.println("❌ Товар не найден");
                    continue;
                }

                System.out.print("Количество: ");
                int quantity = readIntInput();
                System.out.print("Размер: ");
                String size = scanner.nextLine();
                System.out.print("Цвет: ");
                String color = scanner.nextLine();

                BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                OrderItem item = new OrderItem(orderId, productId, quantity, itemPrice, size, color);
                orderItemDao.addOrderItem(item);
            }

            BigDecimal totalAmount = orderItemDao.calculateOrderTotal(orderId);
            order.setTotalAmount(totalAmount);
            orderDao.updateOrder(order);

            System.out.println("✅ Заказ успешно создан с ID: " + orderId + ", сумма: " + totalAmount);
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при создании заказа: " + e.getMessage());
        }
    }

    private static void listAllOrders() {
        try {
            List<Order> orders = orderDao.getAllOrders();
            if (orders.isEmpty()) {
                System.out.println("Список заказов пуст.");
                return;
            }

            System.out.println(" Список всех заказов:");
            System.out.printf("%-5s %-10s %-15s %-15s%n", "ID", "Пользователь", "Сумма", "Статус");
            for (Order o : orders) {
                System.out.printf("%-5d %-10d %-15s %-15s%n",
                        o.getOrderId(), o.getUserId(), o.getTotalAmount(), o.getStatus());
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении заказов: " + e.getMessage());
        }
    }

    private static void getOrderById() {
        System.out.print("\nВведите ID заказа: ");
        int orderId = readIntInput();

        try {
            Order order = orderDao.getOrderById(orderId);
            if (order == null) {
                System.out.println("❌ Заказ не найден");
                return;
            }

            System.out.println("\nИнформация о заказе:");
            System.out.println("ID: " + order.getOrderId());
            System.out.println("Пользователь ID: " + order.getUserId());
            System.out.println("Сумма: " + order.getTotalAmount());
            System.out.println("Статус: " + order.getStatus());
            System.out.println("Создан: " + order.getCreatedAt());

            List<OrderItem> items = orderItemDao.getItemsByOrderId(orderId); // <== исправлено имя метода
            if (items.isEmpty()) {
                System.out.println("Нет товаров в заказе.");
            } else {
                System.out.println("\nТовары в заказе:");
                for (OrderItem item : items) {
                    System.out.printf("Товар ID: %d, Кол-во: %d, Цена: %s, Размер: %s, Цвет: %s%n",
                            item.getProductId(), item.getQuantity(), item.getPrice(), item.getSize(), item.getColor());
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении заказа: " + e.getMessage());
        }
    }
    private static void deleteOrder() {
        System.out.print(" Введите ID заказа для удаления: ");
        int orderId = readIntInput();

        try {
            Order order = orderDao.getOrderById(orderId);
            if (order == null) {
                System.out.println("❌ Заказ не найден");
                return;
            }

            System.out.print("Вы уверены, что хотите удалить заказ ID " + orderId + "? (y/n): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                if (orderDao.deleteOrder(orderId)) {
                    System.out.println("✅ Заказ удалён");
                } else {
                    System.out.println("❌ Не удалось удалить заказ");
                }
            } else {
                System.out.println("Удаление отменено.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении заказа: " + e.getMessage());
        }
    }
    private static void manageOrdersItems() {
        System.out.println("\n=== Управление заказами ===");
        // пример вызова
        // createOrder();
    }
    private static void manageProductsCategories() {
        System.out.println("\n=== Управление заказами ===");
        // пример вызова
        // createOrder();
    }
    // Здесь можно вставить конкретные методы addUser, addProduct, addCategory и т.д., если нужно
}