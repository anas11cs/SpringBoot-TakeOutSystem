package com.tkxel.takeoutsystem;

import com.tkxel.takeoutsystem.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TakeoutsystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(TakeoutsystemApplication.class, args);
	}

}
