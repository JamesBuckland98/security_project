package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.Customer;
import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.repositories.CustomerRepoJPA;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements LoginServiceInterface {
    private LoginRepoJPA loginRepoJPA;
    private CustomerRepoJPA customerRepoJPA;

    @Autowired
    LoginService(LoginRepoJPA lRepo, CustomerRepoJPA cRepo) {
        loginRepoJPA = lRepo;
        customerRepoJPA = cRepo;
    }

    @Override
    @Cacheable("Login")
    public String checkLogin(Login login) {
        List<Login> loginList = loginRepoJPA.findByUsernameAndPassword(login.getUsername(),
                login.getPassword());
        if (loginList.isEmpty()) {
            String permission = "none";
            return permission;
        } else {
            String permission = loginList.get(0).getAccess();
            return permission;
        }
    }

    @Override
    @Cacheable("Username")
    public String getUsername(Login login) {
        List<Login> loginList = loginRepoJPA.findByUsernameAndPassword(login.getUsername(),
                login.getPassword());
        if (loginList.isEmpty()) {
            return null;
        } else {
            String username = loginList.get(0).getUsername();
            return username;
        }
    }

    @Override
    @Cacheable("UserID")
    public Long getUserID(Login login) {
        List<Login> loginList = loginRepoJPA.findByUsernameAndPassword(login.getUsername(),
                login.getPassword());
        if (loginList.isEmpty()) {
            return null;
        } else {
            Long userID = loginList.get(0).getId();
            return userID;
        }
    }

    @Override
    @Cacheable("CustomerEmail")
    public String getCustomerEmail(String username) {
        Optional<Login> login = loginRepoJPA.findByUsername(username);
        if (login.isPresent()) {
            Long loginID = login.get().getId();
            Customer customer = customerRepoJPA.findByLogin_Id(loginID);
            if (customer == null) {
                String customerEmail = "null@null.com";
                return customerEmail;
            } else {
                String customerEmail = customer.getEmail();
                return customerEmail;
            }
        }
        return "null@null.com";
    }

    @Override
    @Cacheable("CustomerPassword")
    public String getCustomerPassword(String username) {
        Optional<Login> login = loginRepoJPA.findByUsername(username);
        if (login.isPresent()) {
            String customerPassword = login.get().getPassword();
            return customerPassword;
        }
        return null;
    }
}
