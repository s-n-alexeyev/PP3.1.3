package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User readUser(long id) {
        User user = null;
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            user = opt.get();
        }
        return user;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByFirstName(name);
    }
    public Optional<User> getUserByEmail(String name) {
        return userRepository.findByEmail(name);
    }
    public List<Role> allRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public Optional<Role> getRoleByRoleName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), //в качестве имени
                user.getPassword(),
                //стримом конвертируем коллекцию ролей в GrantedAuthority
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
