package com.link.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LinkPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkPayApplication.class, args);
    }

}
