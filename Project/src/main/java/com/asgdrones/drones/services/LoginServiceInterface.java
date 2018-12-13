package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.Customer;
import com.asgdrones.drones.domain.Login;

public interface LoginServiceInterface {
    public String checkLogin(Login login);
    public String getUsername(Login login);
    public Long getUserID(Login login);
    public String getCustomerEmail(String username);
    public String getCustomerPassword(String username);
}
