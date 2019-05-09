package com.asgdrones.drones;

import org.hibernate.annotations.Cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AsgDronesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsgDronesApplication.class, args);
    }
}
