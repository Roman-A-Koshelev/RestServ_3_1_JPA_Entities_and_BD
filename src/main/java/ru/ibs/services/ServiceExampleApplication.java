package ru.ibs.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServiceExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceExampleApplication.class, args);
    }

    @RequestMapping("/")
    String index() {
        return "Hello";
    }
}
