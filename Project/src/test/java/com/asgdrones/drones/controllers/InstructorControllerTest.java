package com.asgdrones.drones.controllers;

import com.asgdrones.drones.domain.*;
import com.asgdrones.drones.repositories.InstructorRepoJPA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InstructorControllerTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private InstructorRepoJPA instructorRepoJPA;

    @Test
    public void instructorLocationTest() throws Exception {
        Login login = new Login(1L, "instructor", "TestIns", "123");
        Address address = new Address(1L, "AB123CD", "Cardiff", "Abc Street", 1, null);
        this.entityManager.merge(new Instructor(1L, "Ins", "Tructor", "01234567890", login, address));
        Optional<Instructor> instructor = this.instructorRepoJPA.findById(1L);
        List<String> addresses = this.instructorRepoJPA.getInstructorAddresses(1L);
        assertEquals(instructor.get().getAddress().getCity(), "Cardiff");
    }
}
