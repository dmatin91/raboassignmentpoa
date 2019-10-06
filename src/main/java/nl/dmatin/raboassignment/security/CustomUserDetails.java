package nl.dmatin.raboassignment.security;

import nl.dmatin.raboassignment.model.Role;
import nl.dmatin.raboassignment.model.User;
import nl.dmatin.raboassignment.repository.RoleRepository;
import nl.dmatin.raboassignment.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		return org.springframework.security.core.userdetails.User//
			.withUsername(username)//
			.password(user.getPassword())//
			.authorities(getUserAuthority(user.getRoles()))//
			.accountExpired(false)//
			.accountLocked(false)//
			.credentialsExpired(false)//
			.disabled(false)//
			.build();
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach((role) -> roles.add(new SimpleGrantedAuthority(role.getRole())));

		return new ArrayList<>(roles);
	}

}

