package nl.dmatin.raboassignment.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import nl.dmatin.raboassignment.model.user.Role;

import java.util.List;

@Getter
@Setter
public class UserDataResponseDTO {
	@ApiModelProperty(position = 0)
	private Integer id;
	@ApiModelProperty(position = 1)
	private String username;
	@ApiModelProperty(position = 2)
	private String email;
	@ApiModelProperty(position = 3)
	List<Role> roles;
}
