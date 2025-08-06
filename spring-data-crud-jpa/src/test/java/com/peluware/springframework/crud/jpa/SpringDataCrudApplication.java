package com.peluware.springframework.crud.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringDataCrudApplication {

    public static void main(String[] args) {
        log.info("Starting Spring Data CRUD Application");
        SpringApplication.run(SpringDataCrudApplication.class, args);
    }
}
