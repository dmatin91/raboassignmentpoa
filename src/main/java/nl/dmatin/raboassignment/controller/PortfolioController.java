package nl.dmatin.raboassignment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import nl.dmatin.raboassignment.model.dto.PowerOfAttorneyDTO;
import nl.dmatin.raboassignment.model.user.User;
import nl.dmatin.raboassignment.service.PortfolioService;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(path = "/poa", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class PortfolioController {

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/overview")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@ApiOperation(value = "${PortfolioController.overview}")
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 403, message = "Access denied"),//
		@ApiResponse(code = 404, message = "Not Found") })
	public List<PowerOfAttorneyDTO> overview() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return portfolioService.getUserOverview(currentPrincipalName);
	}

	@GetMapping("/testoverview")
	@ApiOperation(value = "${PortfolioController.testoverview}")
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 403, message = "Access denied"),//
		@ApiResponse(code = 404, message = "Not Found") })
	public List<PowerOfAttorneyDTO> testOverview() {
		return portfolioService.getUserOverview("Super duper company");
	}

	@PostMapping("/findmypoas")
	@ApiOperation(value = "${PortfolioController.findmypoas}")
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 403, message = "Access denied") })
	public User findMyPoas() {
		return portfolioService.findMyPowerOfAttorneys();
	}

	@GetMapping(value = "/{poaid}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@ApiOperation(value = "${PortfolioController.singlePowerOfAttorney}", response = PowerOfAttorneyDTO.class)
	@ApiResponses(value = {//
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 403, message = "Access denied"),//
		@ApiResponse(code = 404, message = "Not Found") })
	public PowerOfAttorneyDTO singlePowerOfAttorney(@ApiParam("Poa_Id") @PathVariable
		String poaid) {
		return portfolioService.getSinglePowerOfAttorneyOverview(poaid);
	}
}
