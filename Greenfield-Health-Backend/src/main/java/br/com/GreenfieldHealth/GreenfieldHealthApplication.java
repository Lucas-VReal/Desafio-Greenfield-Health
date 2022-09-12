package br.com.GreenfieldHealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class GreenfieldHealthApplication {



	public static void main(String[] args) {
		SpringApplication.run(GreenfieldHealthApplication.class, args);
	}


}
