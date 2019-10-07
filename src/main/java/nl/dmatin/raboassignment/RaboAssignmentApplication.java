package nl.dmatin.raboassignment;

import nl.dmatin.raboassignment.model.user.Role;
import nl.dmatin.raboassignment.model.user.User;
import nl.dmatin.raboassignment.repository.RoleRepository;
import nl.dmatin.raboassignment.repository.UserRepository;
import nl.dmatin.raboassignment.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RaboAssignmentApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(RaboAssignmentApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserService userService, UserRepository userRepository) {

		return args -> {

			Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ROLE_ADMIN");
				roleRepository.save(newAdminRole);
			}

			Role userRole = roleRepository.findByRole("ROLE_USER");
			if (userRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("ROLE_USER");
				roleRepository.save(newUserRole);
			}

			User testUser = userRepository.findByUsername("Super duper company");
			if (testUser == null) {
				User newTestUser = new User();
				newTestUser.setEmail("testuser@gmail.com");
				newTestUser.setEnabled(true);
				newTestUser.setId("0001");
				newTestUser.setUsername("Super duper company");
				newTestUser.setPassword("TESTPW");
				newTestUser.setPowerOfAttorneys(new ArrayList<>(Arrays.asList("0001", "0002", "0003")));
				userService.signup(newTestUser);
			}
		};

	}
}
