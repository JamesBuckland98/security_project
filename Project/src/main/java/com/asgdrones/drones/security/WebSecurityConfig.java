package com.asgdrones.drones.security;


import com.asgdrones.drones.repositories.LoginRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private LoginRepoJPA loginRepoJPA;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register", "/register_address", "/register_drone", "/register_customer", "/login")
                .permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("admin")
                .antMatchers("/customer/**").hasAnyAuthority("customer")
                .antMatchers("/instructor/**").hasAnyAuthority("instructor")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(myAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }

    @Autowired
    private MyUserDetailsService userDetailsService;
    WebSecurityConfig(LoginRepoJPA lRepo){
        loginRepoJPA = lRepo;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler(loginRepoJPA);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        System.out.println("setting auth");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
