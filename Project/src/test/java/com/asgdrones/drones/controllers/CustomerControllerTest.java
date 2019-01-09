package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.*;
import com.asgdrones.drones.repositories.CustomerRepoJPA;
import com.asgdrones.drones.services.CourseService;
import com.asgdrones.drones.services.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepoJPA customerRepoJPA;

    //todo
    @Test
    public void customerPageTest() throws Exception {
        Address address = new Address(null, "CF244AN", "Cardiff", "Abby Lane", 4, "");
        Drone drone = new Drone(null, "n/a", "n/a");
        Creation creation = new Creation(null, java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(LocalDate.now().plusYears(2)));
        Login login = new Login(null, "customer", "jbuckland", "password");
        Instructor instructor = new Instructor(null, "james", "buckland", "01895430027", login, address);
        Course course = new Course(null, "Course1", "Type2", "Cardiff", java.sql.Date.valueOf(LocalDate.now()), instructor);
        Customer customer = new Customer(null, "James", "Buckland", java.sql.Date.valueOf("1998-11-16"), "jbuckland@gmail.com", "01895430027", true,
                (float) 2.0, "none", (float) 9.0, "Cardiff", true, false, login, drone, address, course, creation);
        this.mockMvc.perform(get("customer/1")
                .contentType(MediaType.TEXT_XML))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
