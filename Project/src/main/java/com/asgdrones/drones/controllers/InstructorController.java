package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Customer;
import com.asgdrones.drones.domain.Instructor;
import com.asgdrones.drones.enums.Templates;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.repositories.InstructorRepoJPA;
import com.asgdrones.drones.services.CourseService;
import com.asgdrones.drones.services.CustomerService;
import com.asgdrones.drones.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class InstructorController {
    private Cookie[] access;
    private Templates page;
    private InstructorRepoJPA instructorRepoJPA;
    private InstructorService instructorService;
    private CourseService courseService;
    private CustomerService customerService;

    @Autowired
    InstructorController(InstructorRepoJPA iRepo, InstructorService iService, CourseService cService, CustomerService cuService) {
        instructorRepoJPA = iRepo;
        instructorService = iService;
        courseService = cService;
        customerService = cuService;
    }

    @RequestMapping(value = "instructor/{instructorID}", method = RequestMethod.GET)
    public ModelAndView instructor(Model model,
                                   HttpServletRequest request,
                                   @PathVariable("instructorID") Long instructorID) {
        access = request.getCookies();
        for (Cookie obj : access) {
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("instructor")) {
                    page = Templates.INSTRUCTOR_ACCOUNT;
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
        }
        Instructor instructor = instructorService.getInstructor(instructorID);
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
        return new ModelAndView(page.toString(), model.asMap());
    }

    @RequestMapping(value = "instructor/customers/{instructorID}", method = RequestMethod.GET)
    public ModelAndView instructorCustomer(Model model, HttpServletRequest request, @PathVariable("instructorID") Long instructorID){
        access = request.getCookies();
        for (Cookie obj : access) {
            if (obj.getName().equals("Access")) {
                if (obj.getValue().equals("instructor")) {
                    page = Templates.INSTRUCTOR_CUSTOMER;
                } else {
                    page = Templates.ACCESS_DENIED;
                }
            } else {
                page = Templates.ACCESS_DENIED;
            }
            Instructor instructor = instructorService.getInstructor(instructorID);
            List<Course> courses = courseService.findByInstructor(instructor);
            List<Customer> customers = new ArrayList<>();

            for(Course c: courses){
                List<Customer> cust = customerService.findByCourseId(c.getId());
                for(Customer cu: cust) {
                    customers.add(cu);
                }
            }
            //Course course = courses.get(0);
            //List<Customer> customers = customerService.findByCourseId(course.getId());
            model.addAttribute("customers", customers);
        }
        return new ModelAndView(page.toString(), model.asMap());
    }
}
