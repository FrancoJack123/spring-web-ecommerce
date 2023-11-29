package com.example.web.springwebecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
public class SpringWebEcommerceApplication{

    public static void main(String[] args) {
        SpringApplication.run(SpringWebEcommerceApplication.class, args);
    }
    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

}
