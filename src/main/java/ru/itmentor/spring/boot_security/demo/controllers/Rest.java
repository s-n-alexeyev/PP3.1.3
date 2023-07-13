package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.exceptions.BadUserIdException;
import ru.itmentor.spring.boot_security.demo.exceptions.InfoString;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class Rest {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User readUser(@PathVariable long id) {
        User user = userService.readUser(id);
        if (user == null) {
            throw new BadUserIdException("Пользователь с id = " + id + " отсутствует");
        }
        return user;
    }

    @PostMapping("/user")
    public User addNewUser(@RequestBody User user) {
        userService.createOrUpdateUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        userService.createOrUpdateUser(user);
        return user;
    }

    @DeleteMapping("/user/{id}")
    private InfoString deleteUser(@PathVariable long id) {
        User user = userService.readUser(id);
        if (user == null) {
            throw new BadUserIdException("Пользователь с id = " + id + " отсутствует");
        }
        userService.deleteUser(id);
        InfoString info = new InfoString();
        info.setInfo("Пользователь с id = " + id + " удалён");

        return info;
    }
}
