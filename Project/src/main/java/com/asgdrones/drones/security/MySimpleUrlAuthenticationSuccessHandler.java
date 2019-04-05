package com.asgdrones.drones.security;

import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.repositories.LoginRepo;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import com.asgdrones.drones.services.LoginService;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private Optional<Login> user;
    private String username;
    private LoginRepoJPA loginRepoJPA;

    @Autowired
    private MyUserDetailsService userDetailsService;
    MySimpleUrlAuthenticationSuccessHandler(LoginRepoJPA lRepo){
        loginRepoJPA = lRepo;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetURL = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            System.out.println(targetURL);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetURL);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        boolean isInstructor = false;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            user = loginRepoJPA.findByUsername(username);
            System.out.println(user.get().getId());
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("customer")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("instructor")) {
                isInstructor = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("admin")) {
                isAdmin = true;
                break;
            }
        }
        if (isAdmin) {
            return "/admin";
        } else if (isInstructor) {
            return "/instructor";
        } else if (isUser) {
            return "/customer";
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}

class GetUsersCredentials {
    private long userID;
    private LoginRepoJPA loginRepoJPA;

    @Autowired
    GetUsersCredentials(LoginRepoJPA lRepo) {
        loginRepoJPA = lRepo;
    }
    private long getUserID(String username){
        Optional<Login> user = loginRepoJPA.findByUsername(username);
        return user.get().getId();
    }
}
