package com.k4noise.pinspire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class PinspireApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinspireApplication.class, args);
    }

}
