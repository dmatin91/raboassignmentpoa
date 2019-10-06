package nl.dmatin.raboassignment.repository;

import nl.dmatin.raboassignment.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	User findByUsername(String username);
}
