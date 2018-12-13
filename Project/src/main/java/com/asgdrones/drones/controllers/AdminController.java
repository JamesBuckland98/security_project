package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.Address;
import com.asgdrones.drones.domain.Admin;
import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Customer;
import com.asgdrones.drones.enums.Templates;
import com.asgdrones.drones.repositories.AdminRepoJPA;
import com.asgdrones.drones.services.AdminService;
import com.asgdrones.drones.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class AdminController {
    private Cookie[] access;
    private Templates page;
    private AdminService adminService;
    private CourseService courseService;
    @Autowired
    AdminController(AdminService aService, CourseService cService) {
        adminService = aService;
        courseService = cService;
    }

    @RequestMapping(value = "admin/{loginID}", method = RequestMethod.GET)
    public ModelAndView adminAccount(Model model,
                                     HttpServletRequest request,
                                     @PathVariable("loginID") Long loginID) {
        access = request.getCookies();
        for (Cookie obj : access) {
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("admin")) {
                    page = Templates.ADMIN_ACCOUNT;
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
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
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

    @RequestMapping(value = "admin/{loginID}/createcoursedate", method = RequestMethod.GET)
    public ModelAndView createCourseDate(Model model,
                                         HttpServletRequest request,
                                         @PathVariable("loginID") Long loginID){
        access = request.getCookies();
        for (Cookie obj : access) {
            System.out.println(obj.toString());
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("admin")) {
                    page = Templates.CREATE_COURSE_DATE;
                    model.addAttribute("loginID", loginID);
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
        }
//        page = Templates.CREATE_COURSE_DATE;
        Course course = new Course();
        model.addAttribute("course",course);
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "createcoursedate", method = RequestMethod.POST)
    public ModelAndView createCourseDate(Model model,
                                      @Valid Course course,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
        }
        courseService.addCourse(course);
        model.addAttribute("course", course);
        page = Templates.COURSE_CREATED;
        return new ModelAndView(page.toString(), model.asMap());
    }
}
