package nl.dmatin.raboassignment.repository;

import nl.dmatin.raboassignment.model.user.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
	Role findByRole(String role);
}
