package com.yikejian.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author zweig
 */
@SpringBootApplication
//@EnableEurekaClient
//@EnableHystrix
@EnableResourceServer
@EnableJpaAuditing
public class StoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class, args);
    }
}