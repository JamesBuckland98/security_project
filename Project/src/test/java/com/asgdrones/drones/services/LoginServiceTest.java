package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import com.asgdrones.drones.services.LoginServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class LoginServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    LoginServiceInterface loginService;

    @Test
    public void adminLoginTest() throws Exception {
        Login adminLogin = new Login(null, null, "John", "password");
        given(this.loginService.checkLogin(adminLogin)).willReturn("admin");
        Cookie adminCookie = new Cookie("Access", "admin");
        mockMvc.perform(
                post("/login").param("username", "John").param("password", "password")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(cookie().exists("Access"))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("Access", "admin"));
    }

    @Test
    public void instructorLoginTest() throws Exception {
        Login instructorLogin = new Login(null, null, "James", "password");
        given(this.loginService.checkLogin(instructorLogin)).willReturn("instructor");
        Cookie instructorCookie = new Cookie("Access", "instructor");
        mockMvc.perform(
                post("/login").param("username", "James").param("password", "password")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(cookie().exists("Access"))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("Access", "instructor"));
    }

    @Test
    public void customerLoginTest() throws Exception {
        Login customerLogin = new Login(null, null, "Jim", "123");
        given(this.loginService.checkLogin(customerLogin)).willReturn("customer");
        Cookie customerCookie = new Cookie("Access", "customer");
        mockMvc.perform(
                post("/login").param("username", "Jim").param("password", "123")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(cookie().exists("Access"))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("Access", "customer"));
    }
}
