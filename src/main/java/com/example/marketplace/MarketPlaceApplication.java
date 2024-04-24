package com.example.marketplace;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class MarketPlaceApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Almaty"));
    }

    public static void main(String[] args) {
        SpringApplication.run(MarketPlaceApplication.class, args);
    }

}
