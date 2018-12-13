package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.Address;
import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Drone;
import com.asgdrones.drones.enums.Templates;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.services.CourseService;
import com.asgdrones.drones.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class CustomerController {
    private CustomerService customerService;
    private CourseService courseService;
    private Cookie[] access;
    private Templates page;

    @Autowired
    CustomerController(CustomerService cService, CourseService coService) {
        customerService = cService;
        courseService = coService;
    }

    @RequestMapping(value = "customer/{customerID}", method = RequestMethod.GET)
    public ModelAndView customerPage(Model model,
                                     HttpServletRequest request,
                                     @PathVariable("customerID") Long customerID) {
        access = request.getCookies();
        for (Cookie obj : access) {
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("customer")) {
                    page = Templates.CUSTOMER_ACCOUNT;
                    Date dob = customerService.getDob(customerID);
                    Boolean verified = customerService.getVerified(customerID);
                    String name = customerService.getCustomerName(customerID);
                    Course course = customerService.getCourse(customerID);
                    String droneManufacturer = customerService.getDroneManufacturer(customerID);
                    String droneModel = customerService.getDroneModel(customerID);
                    String postCode = customerService.getCustomerPostCode(customerID);
                    Integer houseNumber = customerService.getCustomerHouseNumber(customerID);
                    String houseName = customerService.GetCustomerHouseName(customerID);
                    String street = customerService.getCustomerStreet(customerID);
                    String city = customerService.getCustomerCity(customerID);
                    model.addAttribute("customerID", customerID);
                    model.addAttribute("name", name);
                    model.addAttribute("dob", dob);
                    model.addAttribute("verified",verified);
                    model.addAttribute("courses", course);
                    model.addAttribute("droneManufacturer", droneManufacturer);
                    model.addAttribute("droneModel", droneModel);
                    model.addAttribute("postCode", postCode);
                    model.addAttribute("houseName", houseName);
                    model.addAttribute("houseNumber", houseNumber);
                    model.addAttribute("street", street);
                    model.addAttribute("city", city);
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "customer/{customerID}/course_progression", method = RequestMethod.GET)
    public ModelAndView progressionPage(Model model,
                                        HttpServletRequest request,
                                        @PathVariable("customerID") Long customerID) {
        Integer progression = customerService.getCourseProgression(customerID);
        String name = customerService.getCustomerName(customerID);
        System.out.println(name);
        System.out.println(progression);
        String nameData[] = new String[]{name};
        Integer progressionData[] = new Integer[]{progression};
        page = Templates.CUSTOMER_PROGRESSION;
        model.addAttribute("name", nameData);
        model.addAttribute("progression", progressionData);
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "/customer/{customerID}/update_address", method = RequestMethod.GET)
    public ModelAndView updateAddress(Model model,
                                      HttpServletRequest request,
                                      @PathVariable("customerID") Long customerID) {
        access = request.getCookies();
        for (Cookie obj : access) {
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("customer")) {
                    page = Templates.UPDATE_ADDRESS;
                    Address address = new Address();
                    String postCode = customerService.getCustomerPostCode(customerID);
                    Integer houseNumber = customerService.getCustomerHouseNumber(customerID);
                    String houseName = customerService.GetCustomerHouseName(customerID);
                    String street = customerService.getCustomerStreet(customerID);
                    String city = customerService.getCustomerCity(customerID);
                    model.addAttribute("customerID", customerID);
                    model.addAttribute("postCode", postCode);
                    model.addAttribute("houseName", houseName);
                    model.addAttribute("houseNumber", houseNumber);
                    model.addAttribute("street", street);
                    model.addAttribute("city", city);
                    model.addAttribute("address", address);
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
        }
        return new ModelAndView(String.valueOf(page), model.asMap());
    }

    @RequestMapping(value = "/customer/{customerID}/update_address", method = RequestMethod.POST)
    public RedirectView updateAddress(@Valid Address address,
                                      BindingResult bindingResult,
                                      Model model,
                                      @PathVariable("customerID") Long customerID) {
        model.addAttribute("address", address);
        model.addAttribute("customerID", customerID);
        System.out.println(address);
        System.out.println(customerID);
        customerService.updateAddress(customerID, address);
        return new RedirectView("/customer/{customerID}");
    }

    @RequestMapping(value = "/customer/{customerID}/update_drone", method = RequestMethod.GET)
    public ModelAndView updateDrone(Model model,
                                    HttpServletRequest request,
                                    @PathVariable("customerID") Long customerID) {
        access = request.getCookies();
        for (Cookie obj : access) {
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("customer")) {
                    page = Templates.UPDATE_DRONE;
                    Drone drone = new Drone();
                    model.addAttribute("drone", drone);
                    model.addAttribute("customerID", customerID);
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "/customer/{customerID}/update_drone", method = RequestMethod.POST)
    public RedirectView updateDrone(@Valid Drone drone,
                                    BindingResult bindingResult,
                                    Model model,
                                    @PathVariable("customerID") Long customerID) {
        model.addAttribute("drone", drone);
        model.addAttribute("customerID", customerID);
        customerService.updateDrone(customerID, drone);
        return new RedirectView("/customer/{customerID}");
    }

    @RequestMapping(value = "/customer/{customerID}/add_course", method = RequestMethod.GET)
    public ModelAndView addCourse(Model model,
                                  HttpServletRequest request,
                                  @PathVariable("customerID") Long customerID) {
        access = request.getCookies();
        for (Cookie obj : access) {
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("customer")) {
                    page = Templates.ADD_COURSE;
                    Course course = new Course();
                    List<Course> courseList = courseService.getCourses();
                    model.addAttribute("courses", courseList);
                    model.addAttribute("customerID", customerID);
                    model.addAttribute("newCourse", course);
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "/customer/{customerID}/add_course", method = RequestMethod.POST)
    public RedirectView addCourse(@RequestParam Long course_id,
                                  Model model,
                                  @PathVariable("customerID") Long customerID) {

        System.out.println(course_id);
        customerService.addCourse(customerID, course_id);
        return new RedirectView("/customer/{customerID}");
    }
    @RequestMapping(value = "/customer/{customerID}/verify_customer", method = RequestMethod.GET )
    public RedirectView verifyCustomer(Model model,
                                       HttpServletRequest request,
                                       @PathVariable("customerID") Long customerID){
        customerService.updateCustomer(customerID);
        return new RedirectView("/customer/{customerID}");
    }
}
