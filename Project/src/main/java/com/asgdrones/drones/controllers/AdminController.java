package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.*;
import com.asgdrones.drones.enums.Templates;
import com.asgdrones.drones.repositories.AdminRepoJPA;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import com.asgdrones.drones.services.AdminService;
import com.asgdrones.drones.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    private Cookie[] access;
    private Templates page;
    private AdminService adminService;
    private CourseService courseService;
    private LoginRepoJPA loginRepoJPA;

    @Autowired
    AdminController(AdminService aService, CourseService cService, LoginRepoJPA lRepo) {
        adminService = aService;
        courseService = cService;
        loginRepoJPA = lRepo;
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public ModelAndView AdminAccount(Model model,
                                     HttpServletRequest request,
                                     Authentication authentication) {
        page = Templates.ADMIN_ACCOUNT;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            long loginID = user.get().getId();
            Admin adminDetails = adminService.getAdmin(loginID);
            String name = adminService.getAdminName(loginID);
            String postCode = adminService.GetAdminPostCode(loginID);
            String city = adminService.GetAdminCity(loginID);
            String street = adminService.GetAdminStreet(loginID);
            Integer houseNumber = adminService.GetAdminHouseNumber(loginID);
            List<Customer> customerList = adminService.getCustomers();
            System.out.println(customerList.size());
            System.out.println(Arrays.deepToString(new List[]{customerList}));
            String search = new String();
            model.addAttribute("AdminName", name);
            model.addAttribute("postcode", postCode);
            model.addAttribute("city", city);
            model.addAttribute("street", street);
            model.addAttribute("houseNumber", houseNumber);
            model.addAttribute("customers", customerList);
            model.addAttribute("search", search);
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "admin_search", method = RequestMethod.POST)
    public ModelAndView customerSearch(@RequestParam(value = "search", required = false) String searchQuery,
                                       Model model) {
        List<Customer> customerList = adminService.searchCustomers(searchQuery);
        System.out.println(searchQuery);
        System.out.println(Arrays.deepToString(new List[]{customerList}));
        model.addAttribute("customers", customerList);
        return new ModelAndView("customerSearch", model.asMap());
    }

    @RequestMapping(value = "admin/createCourse", method = RequestMethod.GET)
    public ModelAndView createCourse(Model model,
                                     HttpServletRequest request,
                                     Authentication authentication) {
        page = Templates.CREATE_COURSE_DATE;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            long loginID = user.get().getId();
            model.addAttribute("loginID", loginID);
        }
        Course course = new Course();
        model.addAttribute("course", course);
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "admin/createCourse", method = RequestMethod.POST)
    public ModelAndView createCourse(Model model, @Valid Course course, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
        }
        courseService.addCourse(course);
        model.addAttribute("course", course);
        page = Templates.COURSE_CREATED;
        return new ModelAndView(page.toString(), model.asMap());
    }
}
