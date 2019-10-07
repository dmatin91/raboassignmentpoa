package nl.dmatin.raboassignment.service;

import nl.dmatin.raboassignment.model.dto.PowerOfAttorneyDTO;
import nl.dmatin.raboassignment.model.user.User;

import java.util.List;

public interface PortfolioService {

	List<PowerOfAttorneyDTO> getUserOverview(String username);

	User findMyPowerOfAttorneys();

	PowerOfAttorneyDTO getSinglePowerOfAttorneyOverview(String poaId);

}
