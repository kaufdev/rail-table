package com.kaufdev.railtable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.kaufdev.railtable.transfer.domain")
public class RailTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(RailTableApplication.class, args);
    }

}
