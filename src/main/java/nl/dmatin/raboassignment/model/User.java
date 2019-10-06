package nl.dmatin.raboassignment.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "users")
@Getter
@Setter
public class User {
	@Id
	String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String username;
	private String password;
	private String email;
	private boolean enabled;
	@DBRef
	private Set<Role> roles;
}
