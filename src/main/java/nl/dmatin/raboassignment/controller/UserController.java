package nl.dmatin.raboassignment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import nl.dmatin.raboassignment.model.dto.UserDataDTO;
import nl.dmatin.raboassignment.model.dto.UserDataResponseDTO;
import nl.dmatin.raboassignment.model.user.User;
import nl.dmatin.raboassignment.service.UserService;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(path = "/users", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/signin")
	@ApiOperation(value = "${UserController.signin}")
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 422, message = "Invalid username/password supplied")})
	public String login(//
		@ApiParam("Username") @RequestParam	String username, //
		@ApiParam("Password") @RequestParam String password) {
		return userService.signin(username, password);
	}

	@PostMapping("/signup")
	@ApiOperation(value = "${UserController.signup}")
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 403, message = "Access denied"), //
		@ApiResponse(code = 422, message = "Username is already in use"), //
		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
		return userService.signup(modelMapper.map(user, User.class));
	}

/*	@DeleteMapping(value = "/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "${UserController.delete}")
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 403, message = "Access denied"), //
		@ApiResponse(code = 404, message = "The user doesn't exist"), //
		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public String delete(@ApiParam("Username") @PathVariable String username) {
		userService.delete(username);
		return username;
	}*/

	@GetMapping(value = "/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "${UserController.search}", response = UserDataResponseDTO.class)
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 403, message = "Access denied"), //
		@ApiResponse(code = 404, message = "The user doesn't exist"), //
		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public UserDataResponseDTO search(@ApiParam("Username") @PathVariable
		String username) {
		return modelMapper.map(userService.search(username), UserDataResponseDTO.class);
	}


	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}
}
