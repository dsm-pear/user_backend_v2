package com.dsmpear.main.user_backend_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@SpringBootApplication
@EnableAsync
public class UserBackendV2Application {

	public static void main(String[] args) {
		SpringApplication.run(UserBackendV2Application.class, args);
	}

}
