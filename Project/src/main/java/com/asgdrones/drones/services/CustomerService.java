package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.*;
import com.asgdrones.drones.enums.Courses;
import com.asgdrones.drones.domain.Customer;
import com.asgdrones.drones.repositories.AddressRepoJPA;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.repositories.CustomerRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class CustomerService implements CustomerServiceInterface {
    private CustomerRepoJPA customerRepoJPA;
    private CourseRepoJPA courseRepoJPA;
    private int progression;

    @Autowired
    CustomerService(CustomerRepoJPA cRepo, CourseRepoJPA coRepo) {
        customerRepoJPA = cRepo;
        courseRepoJPA = coRepo;
    }

    @Override
    public List<Customer> findAllById(Iterable<Long> id) {
        return customerRepoJPA.findAllById(id);
    }

    @Override
    public List<Customer> findByCourseId(Long id) {
        return customerRepoJPA.findByCourseId(id);
    }

    @Override
    public Boolean getVerified(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Boolean verified = customer.getVerified();
        return verified;
    }

    @Override
    public void updateAddress(Long loginId, Address address) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginId);
        Customer updatedCustomer = customerRepoJPA.getOne(customer.getId());
        customer.setAddress(address);
        customerRepoJPA.save(customer);
    }

    @Override
    public void updateDrone(Long loginID, Drone drone) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Customer updateCustomer = customerRepoJPA.getOne(customer.getId());
        customer.setDrone(drone);
        customerRepoJPA.save(customer);
    }

    @Override
    public void addCourse(Long loginID, Long courseID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Customer updateCustomer = customerRepoJPA.getOne(customer.getId());
        Optional<Course> courseOptional = courseRepoJPA.findById(courseID);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println(course);
            customer.setCourse(course);
            customerRepoJPA.save(customer);
        }
    }

    @Override
    public void updateCustomer(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        customer.setVerified(true);
        customerRepoJPA.save(customer);
    }

    @Override
    public Integer getCourseProgression(Long id) {
        Optional<Customer> customer = customerRepoJPA.findById(id);
        if (customer.isPresent()) {
            String courseName = customer.get().getCourse().getCourseName();
            System.out.println(courseName);
            if (courseName.equals(String.valueOf(Courses.COURSE_1))) {
                progression = 1;
            } else if (courseName.equals(String.valueOf(Courses.COURSE_2))) {
                progression = 2;
            } else if (courseName.equals(String.valueOf(Courses.COURSE_3))) {
                progression = 3;
            } else {
                progression = 0;
            }
        } else {
            progression = 0;
        }
        return progression;
    }

    @Override
    public String getCustomerName(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        String fullName = customer.getFirstName() + " " + customer.getLastName();
        return fullName;
    }

    @Override
    public java.util.Date getDob(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        java.util.Date dob = customer.getDob();
        return dob;
    }

    @Override
    public Course getCourse(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        Course course = customer.getCourse();
        return course;
    }

    @Override
    public String getDroneManufacturer(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        Drone drone = customer.getDrone();
        String manufacture = drone.getManufacturer();
        return manufacture;
    }

    @Override
    public String getDroneModel(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        Drone drone = customer.getDrone();
        String model = drone.getModel();
        return model;
    }

    @Override
    public String getCustomerPostCode(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String postCode = address.getPostcode();
        return postCode;
    }

    @Override
    public String getCustomerCity(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String city = address.getCity();
        return city;
    }

    @Override
    public String getCustomerStreet(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String street = address.getStreet();
        return street;
    }

    @Override
    public Integer getCustomerHouseNumber(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        Integer houseNumber = address.getHouseNumber();
        return houseNumber;
    }

    @Override
    public String GetCustomerHouseName(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String houseName = address.getHouseName();
        return houseName;
    }


}
