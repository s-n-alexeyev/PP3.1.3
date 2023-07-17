package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dtos.AppMessage;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class Admin  {

    private final UserService userService;

    @PostMapping("/admin")
    public User addNewUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    @PutMapping("/admin")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AppMessage(HttpStatus.NOT_FOUND.value(), "Пользователь не найден"));
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            User user = userService.readUser(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AppMessage(HttpStatus.NOT_FOUND.value(), "Пользователь с id=" + id + " отсутствует"));
            } else {
                userService.deleteUser(id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new AppMessage(HttpStatus.OK.value(), "Пользователь с id=" + id + " удален"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера"));
        }
    }

}
