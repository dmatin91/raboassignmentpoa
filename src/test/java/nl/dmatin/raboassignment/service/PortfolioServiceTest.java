package nl.dmatin.raboassignment.service;

import nl.dmatin.raboassignment.client.PowerOfAttorneyClient;
import nl.dmatin.raboassignment.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PowerOfAttorneyClient powerOfAttorneyClient;

	@Mock
	private ConversionService conversionService;

	@Mock
	private CardService cardService;

	@Mock
	private AccountService accountService;

	@InjectMocks
	private PortfolioService portfolioService;


}
