package com.aarya.election;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectionCommissionSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElectionCommissionSystemApplication.class, args);
        System.out.println("✅ Election Commission System is running...");
    }
}
