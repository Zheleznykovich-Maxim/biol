package com.example.biol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication
public class BiolApplication {
	public static void main(String[] args) {
		SpringApplication.run(BiolApplication.class, args);
	}

}
