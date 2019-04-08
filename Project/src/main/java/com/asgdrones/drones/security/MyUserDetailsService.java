package com.asgdrones.drones.security;

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

import java.util.Optional;
@Configuration
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginRepoJPA loginRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login user = loginRepo.findByUsername(username).get();
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
