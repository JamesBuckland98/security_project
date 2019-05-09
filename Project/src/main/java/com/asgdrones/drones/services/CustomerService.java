package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.*;
import com.asgdrones.drones.enums.Courses;
import com.asgdrones.drones.domain.Customer;
import com.asgdrones.drones.repositories.AddressRepoJPA;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.repositories.CustomerRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("customerList")
    public List<Customer> findAllById(Iterable<Long> id) {
        return customerRepoJPA.findAllById(id);
    }

    @Override
    @Cacheable("customerList")
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
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        if (customer != null) {
            String courseType = customer.getCourse().getCourseType();
            System.out.println(courseType);
            if (courseType.equals(String.valueOf(Courses.COURSE_1))) {
                progression = 1;
            } else if (courseType.equals(String.valueOf(Courses.COURSE_2))) {
                progression = 2;
            } else if (courseType.equals(String.valueOf(Courses.COURSE_3))) {
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
    @Cacheable("customerName")
    public String getCustomerName(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        String fullName = customer.getFirstName() + " " + customer.getLastName();
        return fullName;
    }

    @Override
    @Cacheable("customerDOB")
    public java.util.Date getDob(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        java.util.Date dob = customer.getDob();
        return dob;
    }

    @Override
    @Cacheable("CustomerCourse")
    public Course getCourse(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        Course course = customer.getCourse();
        return course;
    }

    @Override
    @Cacheable("CustomerDroneManufacturer")
    public String getDroneManufacturer(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        Drone drone = customer.getDrone();
        String manufacture = drone.getManufacturer();
        return manufacture;
    }

    @Override
    @Cacheable("CustomerDroneModel")
    public String getDroneModel(Long id) {
        Customer customer = customerRepoJPA.findByLogin_Id(id);
        Drone drone = customer.getDrone();
        String model = drone.getModel();
        return model;
    }

    @Override
    @Cacheable("CustomerPostCode")
    public String getCustomerPostCode(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String postCode = address.getPostcode();
        return postCode;
    }

    @Override
    @Cacheable("CustomerCity")
    public String getCustomerCity(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String city = address.getCity();
        return city;
    }

    @Override
    @Cacheable("CustomerStreet")
    public String getCustomerStreet(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String street = address.getStreet();
        return street;
    }

    @Override
    @Cacheable("CustomerHouseNumber")
    public Integer getCustomerHouseNumber(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        Integer houseNumber = address.getHouseNumber();
        return houseNumber;
    }

    @Override
    @Cacheable("CustomerHouseName")
    public String GetCustomerHouseName(Long loginID) {
        Customer customer = customerRepoJPA.findByLogin_Id(loginID);
        Address address = customer.getAddress();
        String houseName = address.getHouseName();
        return houseName;
    }


}
