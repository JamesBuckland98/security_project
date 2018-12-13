package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.*;
import com.asgdrones.drones.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class RegisterService implements RegisterServiceInterface {
    private CustomerRepoJPA customerRepoJPA;
    private LoginRepoJPA loginRepoJPA;
    private AddressRepoJPA addressRepoJPA;
    private DroneRepoJPA droneRepoJPA;
    private CreationRepoJPA creationRepoJPA;

    @Autowired
    RegisterService(CustomerRepoJPA cRepo, LoginRepoJPA lRepo, AddressRepoJPA aRepo, DroneRepoJPA dRepo, CreationRepoJPA crRepo) {
        customerRepoJPA = cRepo;
        loginRepoJPA = lRepo;
        addressRepoJPA = aRepo;
        droneRepoJPA = dRepo;
        creationRepoJPA = crRepo;
    }

    @Override
    public void upload(Address address, Drone drone, Customer customer, Login login) {
        Address instructorAddress = new Address(null, "1234567", "Cardiff", "123 Street", 4, "");
        Login instructorLogin = new Login(null, "instructor", "test", "test");
        Instructor instructor = new Instructor(null, "null", "null", "11111111111", instructorLogin, instructorAddress);
        Course course = new Course(null, "", "", "cardiff", Date.valueOf(LocalDate.now()), null);
        Creation creation = new Creation(null, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now().plusYears(2)));
        System.out.println(creation);
        Drone newDrone = new Drone(null, drone.getManufacturer(), drone.getModel());
        Login newLogin = new Login(null, "customer", login.getUsername(), login.getPassword());
        Customer newCustomer = new Customer(null, customer.getFirstName(),
                customer.getLastName(), customer.getDob(),
                customer.getEmail(), customer.getPhoneNumber(),
                customer.getPaid(), customer.getHoursOfFlying(),
                customer.getDisability(), customer.getEnglishSpeakingLevel(), customer.getPreferredGSLocation(),
                customer.getInsured(),false, newLogin, newDrone, address,null,creation);
        System.out.println(newCustomer);
//        droneRepoJPA.save(newDrone);
//        loginRepoJPA.save(newLogin);
//        addressRepoJPA.save(address);
//        creationRepoJPA.save(creation);
        customerRepoJPA.save(newCustomer);

    }
}
