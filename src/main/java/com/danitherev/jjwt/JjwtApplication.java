package com.danitherev.jjwt;

import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.repository.RoleRepository;
import com.danitherev.jjwt.repository.UserRepository;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableAsync
public class JjwtApplication {
	public static void main(String[] args) {

		SpringApplication.run(JjwtApplication.class, args);
	}

//	@PersistenceContext
//	private EntityManager entityManager;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private RoleRepository roleRepository;
//	@Autowired
//	private UserRepository userRepository;
//
//
//	@Bean
//	@Transactional
//	CommandLineRunner commandLineRunner() {
//		return args -> {
//			try {
//				Faker faker = new Faker();
//
//				Role role = new Role();
//				role.setName("INVITADO");
//				roleRepository.save(role);
//
//				for (int i = 0; i < 1000; i++) {
//					User user = new User();
//					user.setFirstName(faker.name().firstName());
//					user.setLastName(faker.name().lastName());
//					user.setUsername(faker.name().username());
//					user.setEmail(faker.internet().emailAddress());
//					user.setPassword(passwordEncoder.encode("12345678"));
//					user.setRole(role);
//					userRepository.save(user);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				// Manejo de la excepción
//			}
//		};
//	}
}
