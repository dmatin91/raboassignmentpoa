package nl.dmatin.raboassignment.model.user;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Getter
@Setter
public class Role {
	@Id
	String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String role;
}
