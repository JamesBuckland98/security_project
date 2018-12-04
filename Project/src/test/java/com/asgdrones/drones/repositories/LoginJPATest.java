package com.asgdrones.drones.repositories;

import com.asgdrones.drones.domain.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class LoginJPATest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LoginRepoJPA repository;

    @Test
    public void loginAdminTest() throws Exception{
        this.entityManager.merge(new Login(1L,"admin","John","password"));
        List<Login> login = this.repository.findByUsernameAndPassword("John","password");
        assertThat(login.get(0).getUsername()).isEqualTo("John");
        assertThat(login.get(0).getPassword()).isEqualTo("password");
        assertThat(login.get(0).getAccess()).isEqualTo("admin");
    }
    @Test
    public void loginInstructorTest() throws Exception{
        this.entityManager.merge(new Login(1L,"instructor","Jim","password"));
        List<Login> login = this.repository.findByUsernameAndPassword("Jim","password");
        assertThat(login.get(0).getUsername()).isEqualTo("Jim");
        assertThat(login.get(0).getPassword()).isEqualTo("password");
        assertThat(login.get(0).getAccess()).isEqualTo("instructor");
    }
    @Test
    public void loginCustomerTest() throws Exception{
        this.entityManager.merge(new Login(1L,"customer","Jan","password"));
        List<Login> login = this.repository.findByUsernameAndPassword("Jan","password");
        assertThat(login.get(0).getUsername()).isEqualTo("Jan");
        assertThat(login.get(0).getPassword()).isEqualTo("password");
        assertThat(login.get(0).getAccess()).isEqualTo("customer");
    }

    @Test
    public void loginDeniedTest() throws Exception{
        this.entityManager.merge(new Login(1L,"customer","Jess","password"));
        List<Login> login = this.repository.findByUsernameAndPassword("Jon","password");
        assertThat(login).isEmpty();
    }
}
