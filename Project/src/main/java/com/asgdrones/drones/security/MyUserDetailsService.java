package com.asgdrones.drones.security;

import com.asgdrones.drones.controllers.ErrorController;
import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.repositories.LoginRepo;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
@Configuration
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginRepoJPA loginRepo;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException(ip +" "+"is blocked blocked due to too many login attempts");
        }
        Login user = loginRepo.findByUsername(username).get();
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
