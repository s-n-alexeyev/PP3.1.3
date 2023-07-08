package ru.itmentor.spring.boot_security.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+$", message = "Введите только буквы")
    @Size(min = 2, max = 30, message = "Имя должно быть в переделах от 2 до 30")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email неверный")
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 5, max = 255, message = "Пароль должен быть в переделах от 6 до 255")
    private String password;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Role> roles = new HashSet<>();

}
