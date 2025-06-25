package com.onlineshop.springboot.service;

import com.onlineshop.springboot.entity.User;
import com.onlineshop.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Получить всех пользователей
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Найти пользователя по ID
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Создать/обновить пользователя
    public User save(User user) {
        return userRepository.save(user);
    }

    // Удалить пользователя
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Найти по email (дополнительный метод)
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
