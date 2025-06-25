package com.onlineshop.springboot.controller;

import com.onlineshop.springboot.entity.User;
import com.onlineshop.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Создать нового пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // Обновить пользователя
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);  // Убедимся, что ID совпадает
        return userService.save(user);
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    // Найти пользователя по email (дополнительный эндпоинт)
    @GetMapping("/by-email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
}
