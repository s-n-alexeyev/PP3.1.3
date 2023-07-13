package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.itmentor.spring.boot_security.demo.handlers.CustomAuthenticationFailureHandler;
import ru.itmentor.spring.boot_security.demo.handlers.CustomAuthenticationSuccessHandler;
import ru.itmentor.spring.boot_security.demo.handlers.CustomUrlLogoutSuccessHandler;
import ru.itmentor.spring.boot_security.demo.services.UserDetailService;

import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@EnableWebSecurity
//        (debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // сервис, с помощью которого тащим пользователя
    private final UserDetailService userDetailService;

    // класс, в котором описана логика перенаправления пользователей по ролям
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    // класс, в котором описана логика при неудачной авторизации
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    // класс, в котором описана логика при удачной авторизации
    private final CustomUrlLogoutSuccessHandler urlLogoutSuccessHandler;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailService userDetailService,
                          CustomAuthenticationSuccessHandler authenticationSuccessHandler,
                          CustomAuthenticationFailureHandler authenticationFailureHandler,
                          CustomUrlLogoutSuccessHandler urlLogoutSuccessHandler,
                          PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.urlLogoutSuccessHandler = urlLogoutSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // конфигурация для прохождения аутентификации
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //выключаем кроссдоменную секьюрность
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/", "/css/*", "/img/*").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        http.formLogin()
                .loginPage("/") // указываем страницу с формой логина
                .permitAll()  // даем доступ к форме логина всем
                .successHandler(authenticationSuccessHandler) //указываем логику обработки при удачном логине
                .failureHandler(authenticationFailureHandler) //указываем логику обработки при неудачном логине
                .usernameParameter("email") // Указываем параметры логина и пароля с формы логина
                .passwordParameter("password");

        http.logout()
                .permitAll() // разрешаем делать логаут всем
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true) // сделать невалидной текущую сессию
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/?logout") // указываем URL при удачном логауте
                .logoutSuccessHandler(urlLogoutSuccessHandler);
    }

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
}