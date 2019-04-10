package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.Address;
import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Drone;
import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.enums.Templates;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import com.asgdrones.drones.services.CourseService;
import com.asgdrones.drones.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.Optional;

@Controller
public class CustomerController {
    private CustomerService customerService;
    private CourseService courseService;
    private Templates page;
    private LoginRepoJPA loginRepoJPA;

    @Autowired
    CustomerController(CustomerService cService, CourseService coService, LoginRepoJPA lRepo) {
        customerService = cService;
        courseService = coService;
        loginRepoJPA = lRepo;
    }

    @RequestMapping(value = "customer", method = RequestMethod.GET)
    public ModelAndView customerProfilePage(Model model,
                                            HttpServletRequest request, Authentication authentication) {
        page = Templates.CUSTOMER_ACCOUNT;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            Date dob = customerService.getDob(user.get().getId());
            Boolean verified = customerService.getVerified(user.get().getId());
            String name = customerService.getCustomerName(user.get().getId());
            Course course = customerService.getCourse(user.get().getId());
            String droneManufacturer = customerService.getDroneManufacturer(user.get().getId());
            String droneModel = customerService.getDroneModel(user.get().getId());
            String postCode = customerService.getCustomerPostCode(user.get().getId());
            Integer houseNumber = customerService.getCustomerHouseNumber(user.get().getId());
            String houseName = customerService.GetCustomerHouseName(user.get().getId());
            String street = customerService.getCustomerStreet(user.get().getId());
            String city = customerService.getCustomerCity(user.get().getId());
            model.addAttribute("customerID", user.get().getId());
            model.addAttribute("name", name);
            model.addAttribute("dob", dob);
            model.addAttribute("verified", verified);
            model.addAttribute("courses", course);
            model.addAttribute("droneManufacturer", droneManufacturer);
            model.addAttribute("droneModel", droneModel);
            model.addAttribute("postCode", postCode);
            model.addAttribute("houseName", houseName);
            model.addAttribute("houseNumber", houseNumber);
            model.addAttribute("street", street);
            model.addAttribute("city", city);

        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "customer/course_progression", method = RequestMethod.GET)
    public ModelAndView customerProgress(Model model,
                                         HttpServletRequest request,
                                         Authentication authentication) {
        page = Templates.CUSTOMER_PROGRESSION;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            Integer progression = customerService.getCourseProgression(user.get().getId());
            String name = customerService.getCustomerName(user.get().getId());
            String nameData[] = new String[]{name};
            Integer progressionData[] = new Integer[]{progression};
            model.addAttribute("name", nameData);
            model.addAttribute("progression", progressionData);
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "/customer/update_address", method = RequestMethod.GET)
    public ModelAndView updateCustomerAddress(Model model,
                                              HttpServletRequest request,
                                              Authentication authentication) {
        page = Templates.UPDATE_ADDRESS;
        Object principal = authentication.getPrincipal();
        Address address = new Address();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            String postCode = customerService.getCustomerPostCode(user.get().getId());
            Integer houseNumber = customerService.getCustomerHouseNumber(user.get().getId());
            String houseName = customerService.GetCustomerHouseName(user.get().getId());
            String street = customerService.getCustomerStreet(user.get().getId());
            String city = customerService.getCustomerCity(user.get().getId());
            model.addAttribute("customerID", user.get().getId());
            model.addAttribute("postCode", postCode);
            model.addAttribute("houseName", houseName);
            model.addAttribute("houseNumber", houseNumber);
            model.addAttribute("street", street);
            model.addAttribute("city", city);
            model.addAttribute("address", address);
        }
        return new ModelAndView(String.valueOf(page), model.asMap());
    }

    @RequestMapping(value = "customer/update_address", method = RequestMethod.POST)
    public RedirectView updateCustomerAddress(@Valid Address address,
                                              BindingResult bindingResult,
                                              Model model,
                                              Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            model.addAttribute("address", address);
            model.addAttribute("customerID", user.get().getId());
            System.out.println(address);
            System.out.println(user.get().getId());
            customerService.updateAddress(user.get().getId(), address);
        }
        return new RedirectView("/customer");
    }

    @RequestMapping(value = "/customer/update_drone", method = RequestMethod.GET)
    public ModelAndView updateCustomerDrone(Model model,
                                            HttpServletRequest request,
                                            Authentication authentication) {
        page = Templates.UPDATE_DRONE;
        Drone drone = new Drone();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            model.addAttribute("drone", drone);
            model.addAttribute("customerID", user.get().getId());
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "/customer/update_drone", method = RequestMethod.POST)
    public RedirectView updateCustomerDrone(@Valid Drone drone, BindingResult bindingResult,
                                            Model model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            model.addAttribute("drone", drone);
            model.addAttribute("customerID", user.get().getId());
            customerService.updateDrone(user.get().getId(), drone);
        }
        return new RedirectView("/customer");
    }

    @RequestMapping(value = "customer/add_course", method = RequestMethod.GET)
    public ModelAndView addCustomerCourse(Model model,
                                          HttpServletRequest request,
                                          Authentication authentication) {
        Object principal = authentication.getPrincipal();
        page = Templates.ADD_COURSE;
        Course course = new Course();
        List<Course> courseList = courseService.getCourses();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            model.addAttribute("courses", courseList);
            model.addAttribute("customerID", user.get().getId());
            model.addAttribute("newCourse", course);
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "/customer/add_course", method = RequestMethod.POST)
    public RedirectView addCustomerCourse(@RequestParam Long course_id,
                                          Model model,
                                          Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            customerService.addCourse(user.get().getId(), course_id);
        }
        return new RedirectView("/customer");
    }

    @RequestMapping(value = "/customer/verify", method = RequestMethod.GET)
    public RedirectView verifyCustomerAccount(Model model,
                                              HttpServletRequest request,
                                              Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            customerService.updateCustomer(user.get().getId());
        }
        return new RedirectView("/customer");
    }
}
