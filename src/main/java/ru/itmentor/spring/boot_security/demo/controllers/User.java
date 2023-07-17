package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.dtos.AppMessage;
import ru.itmentor.spring.boot_security.demo.services.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class User {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> showAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера"));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> readUser(@PathVariable long id) {
        try {
            ru.itmentor.spring.boot_security.demo.models.User user = userService.readUser(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AppMessage(HttpStatus.NOT_FOUND.value(), "Пользователь с id=" + id + " отсутствует"));
            } else {
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера"));
        }
    }
}
