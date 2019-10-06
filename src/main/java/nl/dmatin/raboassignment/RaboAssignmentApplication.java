package nl.dmatin.raboassignment;

import nl.dmatin.raboassignment.model.user.Role;
import nl.dmatin.raboassignment.repository.RoleRepository;

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
	CommandLineRunner init(RoleRepository roleRepository) {

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
		};

	}
}
