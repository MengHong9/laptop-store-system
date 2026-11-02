package org.example.laptopstoresystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LaptopStoreSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaptopStoreSystemApplication.class, args);
    }

}
