package nl.dmatin.raboassignment.service;

import nl.dmatin.raboassignment.model.user.User;

public interface UserService {

	String signin(String username, String password);

	String signup(User user);

	User search(String username);

	String refresh(String username);
}
