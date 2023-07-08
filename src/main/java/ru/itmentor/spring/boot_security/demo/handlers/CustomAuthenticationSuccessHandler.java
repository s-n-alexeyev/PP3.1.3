////package ru.itmentor.spring.boot_security.demo.handler;
////
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.authority.AuthorityUtils;
////import org.springframework.security.web.DefaultRedirectStrategy;
////import org.springframework.security.web.RedirectStrategy;
////import org.springframework.security.web.WebAttributes;
////import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//////import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
////import org.springframework.stereotype.Component;
////
////
////import javax.servlet.ServletException;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////import javax.servlet.http.HttpSession;
////import java.io.IOException;
////import java.util.Set;
////
////@Component
//////public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
////public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
////
////    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
////    @Override
////    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
////        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
////
////        if (roles.contains("ROLE_ADMIN")) {
////            setDefaultTargetUrl("/admin");
////        } else if(roles.contains("ROLE_USER")) {
////            setDefaultTargetUrl("/user");
////        } else {
////            setDefaultTargetUrl("/");
////        }
////
////        super.onAuthenticationSuccess(request, response, authentication);
////    }
////}
//
//
//
//package ru.itmentor.spring.boot_security.demo.handler;
//
//        import org.springframework.security.core.Authentication;
//        import org.springframework.security.core.authority.AuthorityUtils;
//        import org.springframework.security.web.DefaultRedirectStrategy;
//        import org.springframework.security.web.RedirectStrategy;
//        import org.springframework.security.web.WebAttributes;
//        import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//        import org.springframework.stereotype.Component;
//
//        import javax.servlet.ServletException;
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//        import javax.servlet.http.HttpSession;
//        import java.io.IOException;
//        import java.util.Set;
//
//
//@Component
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        handle(httpServletRequest, httpServletResponse, authentication);
//        clearAuthenticationAttributes(httpServletRequest);
//    }
//
//    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        String targetUrl = determineTargetUrl(authentication);
//        if (response.isCommitted()) {
//            return;
//        }
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//    }
//
//    private String determineTargetUrl(Authentication authentication) {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//
//        if (roles.contains("ROLE_ADMIN")) {
//            return "/admin";
//        } else if (roles.contains("ROLE_USER")) {
//            return "/user";
//        } else {
//            return "/accessDenied";
//        }
//
//    }
//
//    private void clearAuthenticationAttributes(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return;
//        }
//        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }
//}

package ru.itmentor.spring.boot_security.demo.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            setDefaultTargetUrl("/admin");
        } else if(roles.contains("ROLE_USER")) {
            setDefaultTargetUrl("/user");
        } else {
            setDefaultTargetUrl("/");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}