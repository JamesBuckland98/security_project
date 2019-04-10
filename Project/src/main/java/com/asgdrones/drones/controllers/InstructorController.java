package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Customer;
import com.asgdrones.drones.domain.Instructor;
import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.enums.Templates;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.repositories.InstructorRepoJPA;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import com.asgdrones.drones.services.CourseService;
import com.asgdrones.drones.services.CustomerService;
import com.asgdrones.drones.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class InstructorController {
    private Cookie[] access;
    private Templates page;
    private InstructorRepoJPA instructorRepoJPA;
    private InstructorService instructorService;
    private CourseService courseService;
    private CustomerService customerService;
    private LoginRepoJPA loginRepoJPA;

    @Autowired
    InstructorController(InstructorRepoJPA iRepo, InstructorService iService, CourseService cService, CustomerService cuService, LoginRepoJPA lRepo) {
        instructorRepoJPA = iRepo;
        instructorService = iService;
        courseService = cService;
        customerService = cuService;
        loginRepoJPA = lRepo;
    }

    @RequestMapping(value = "/instructor", method = RequestMethod.GET)
    public ModelAndView instructorAccount(Model model,
                                          HttpServletRequest request,
                                          Authentication authentication){
        page = Templates.INSTRUCTOR_ACCOUNT;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            long loginID = user.get().getId();
            Instructor instructor = instructorService.getInstructor(loginID);
            System.out.println(instructor);
            List<Course> courses = courseService.findByInstructor(instructor);
            List<String> addresses = instructorService.getInstructorAddress(instructor.getId());
            List<Date> dates = instructorService.getCourseDates(instructor.getId());
            System.out.println(courses);
            System.out.println(addresses);
            System.out.println(dates);
            model.addAttribute("addresses", addresses);
            model.addAttribute("dates", dates);
            model.addAttribute("courses", courses);
        }
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "instructor/customers")
    public ModelAndView instructorCustomers(Model model,
                                            HttpServletRequest request,
                                            Authentication authentication){
        Object principal = authentication.getPrincipal();
        page = Templates.INSTRUCTOR_CUSTOMER;
        if (principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            Optional<Login> user = loginRepoJPA.findByUsername(username);
            long loginID = user.get().getId();
            Instructor instructor = instructorService.getInstructor(loginID);
            List<Course> courses = courseService.findByInstructor(instructor);
            List<Customer> customers = new ArrayList<>();

            for(Course c: courses){
                List<Customer> cust = customerService.findByCourseId(c.getId());
                for(Customer cu: cust) {
                    customers.add(cu);
                }
            }
        }
        return new ModelAndView(page.toString(), model.asMap());
    }
}
