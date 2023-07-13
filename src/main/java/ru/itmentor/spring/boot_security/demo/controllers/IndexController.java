
package ru.itmentor.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import ru.itmentor.spring.boot_security.demo.services.UserDetailService;
import ru.itmentor.spring.boot_security.demo.configs.LoginException;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {
    private final UserDetailService userDetailService;

    @Autowired
    public IndexController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("")
    public String welcomePage(Model model, HttpSession session,
                              @SessionAttribute(required = false, name = "Authentication-Exception") LoginException authenticationException,
                              @SessionAttribute(required = false, name = "Authentication-Name") String authenticationName) {
        userDetailService.tryIndex(model, session, authenticationException, authenticationName);

        return "index";
    }
}

