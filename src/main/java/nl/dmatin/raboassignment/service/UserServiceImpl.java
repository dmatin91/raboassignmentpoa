package nl.dmatin.raboassignment.service;

import nl.dmatin.raboassignment.exception.CustomException;
import nl.dmatin.raboassignment.model.user.User;
import nl.dmatin.raboassignment.repository.RoleRepository;
import nl.dmatin.raboassignment.repository.UserRepository;
import nl.dmatin.raboassignment.security.JwtTokenProvider;

import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
		}
		catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public String signup(User user) {
		User userExists = userRepository.findByUsername(user.getEmail());
		if (userExists == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByRole("ROLE_USER"))));
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		} else {
			throw new CustomException("Username is already exist", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public User search(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user;
	}

	@Override
	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	}
}
