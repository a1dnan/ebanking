package com.aalil.ebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EbankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingApplication.class, args);
    }

}
