package com.asgdrones.drones.controllers;

import com.asgdrones.drones.services.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc

public class RegisterFormTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RegisterService registerService;


    @Test
    public void shouldReturnHomePage() throws Exception {
        this.mockMvc.perform(get("/register")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("First Name")));
    }

    @Test
    public void shouldReturnTerms() throws Exception{

        this.mockMvc.perform(get("/register")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("register")));
    }




}

