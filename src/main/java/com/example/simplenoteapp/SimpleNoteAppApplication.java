package com.example.simplenoteapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SimpleNoteAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleNoteAppApplication.class, args);
    }

}
