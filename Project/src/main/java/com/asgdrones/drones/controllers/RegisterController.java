package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.*;
import com.asgdrones.drones.services.EmailService;
import com.asgdrones.drones.services.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@SessionAttributes({"customer", "address", "drone", "login"})
@Controller
public class RegisterController {

    private RegisterService registerService;
    private EmailService emailService;

    @Autowired
    public RegisterController(RegisterService rService, EmailService eService) {

        registerService = rService;
        emailService = eService;
    }

    static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView addRegister(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return new ModelAndView("register", model.asMap());
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public RedirectView addRegisterForm(@Valid Customer customer, BindingResult bindingResult, Model model) {
        //binding result stops it from being null

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
        }
        System.out.println("Form Received");
        System.out.println(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("firstName", customer.getFirstName());
        model.addAttribute("lastName", customer.getLastName());
        model.addAttribute("dob", customer.getDob());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("phoneNumber", customer.getPhoneNumber());
        model.addAttribute("hoursOfFlying", customer.getHoursOfFlying());
        model.addAttribute("disability", customer.getDisability());
        model.addAttribute("englishSpeakingLevel", customer.getEnglishSpeakingLevel());
        model.addAttribute("paid", customer.getPaid());
        model.addAttribute("insured", customer.getInsured());
        model.addAttribute("preferredLocation", customer.getPreferredGSLocation());
        // call service to save charity
        // saving to db

        return new RedirectView("/register_address", true);
    }


    @RequestMapping(value = "/register_address", method = RequestMethod.GET)
    public ModelAndView addRegisterAddress(@SessionAttribute Customer customer, Model model) {
        System.out.println(customer);
        Address address = new Address();
        model.addAttribute("address", address);
        return new ModelAndView("registerAddress", model.asMap());
    }


    @RequestMapping(path = "/register_address", method = RequestMethod.POST)
    public RedirectView addAddressForm(@Valid Address address, BindingResult bindingResult, Model model) {
        //binding result stops it from being null

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
        }
        System.out.println("Form Received");
        System.out.println(address);
        model.addAttribute("address", address);
        model.addAttribute("Street", address.getStreet());
        model.addAttribute("HouseName", address.getHouseName());
        model.addAttribute("HouseNumber", address.getHouseNumber());
        model.addAttribute("Postcode", address.getPostcode());
        model.addAttribute("City", address.getCity());


        return new RedirectView("/register_drone", true);
    }

    @RequestMapping(value = "/register_drone", method = RequestMethod.GET)
    public ModelAndView addDrone(@SessionAttribute Address address, @SessionAttribute Customer customer, Model model) {
        System.out.println(address);
        Drone drone = new Drone();
        model.addAttribute("address", address);
        model.addAttribute("drone", drone);
        return new ModelAndView("registerDrone", model.asMap());
    }


    @RequestMapping(path = "/register_drone", method = RequestMethod.POST)
    public RedirectView addDrone(@Valid Drone drone, BindingResult bindingResult, Model model) {
        //binding result stops it from being null

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
        }
        System.out.println("Form Received");
        System.out.println(drone);
        model.addAttribute("model", drone.getManufacturer());
        model.addAttribute("manufacturer", drone.getModel());


        return new RedirectView("/register_customer", true);
    }


    @RequestMapping(value = "/register_customer", method = RequestMethod.GET)
    public ModelAndView addRegisterCustomer(@SessionAttribute Address address, @SessionAttribute Customer customer,
                                            @SessionAttribute Drone drone, Model model) {
        Login login = new Login();
        System.out.println(drone);
        System.out.println(address);
        System.out.println(customer);
        model.addAttribute("login", login);
        return new ModelAndView("registerCustomer", model.asMap());
    }


    @RequestMapping(path = "/register_customer", method = RequestMethod.POST)
    public RedirectView addLoginForm(@Valid Login login, @SessionAttribute Address address,
                               @SessionAttribute Customer customer, @SessionAttribute Drone drone,
                               BindingResult bindingResult, Model model) {
        //binding result stops it from being null
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
        }
        System.out.println("Form Received");
        login.setAccess("customer");
        System.out.println(login);
        System.out.println(customer);
        System.out.println(address);
        System.out.println(drone);
        model.addAttribute("login", login);
        model.addAttribute("HouseName", login.getUsername());
        model.addAttribute("HouseNumber", login.getPassword());
        try {
            emailService.sendEmail(customer.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        registerService.upload(address, drone, customer, login);
        return new RedirectView("/login");
    }

}
