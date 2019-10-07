package nl.dmatin.raboassignment.client;

import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;
import nl.dmatin.raboassignment.model.poa.PoaDirection;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
public class PowerOfAttorneyClientImplTest {

	@Value("classpath:single.poa.json")
	File currentPoaResponse;

	@Value("classpath:debit.card.json")
	File currentDebitCardResponse;

	@Value("classpath:credit.card.json")
	File currentCreditCardResponse;

	@Value("classpath:account.json")
	File currentAccountResponse;


	@Test
	public void getPowerOfAttorney() throws IOException {
		ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
		ClientApiPowerOfAttorneyDTO clientApiPowerOfAttorneyDTO = objectMapper.readValue(currentPoaResponse, ClientApiPowerOfAttorneyDTO.class);

		Assert.assertNotNull(clientApiPowerOfAttorneyDTO);
		Assert.assertEquals("1234", clientApiPowerOfAttorneyDTO.getId());
		Assert.assertEquals("Super duper company", clientApiPowerOfAttorneyDTO.getGrantor());

		Assert.assertNotNull(clientApiPowerOfAttorneyDTO.getAuthorizations());
		Assert.assertEquals(PoaDirection.GIVEN, clientApiPowerOfAttorneyDTO.getDirection());

		Assert.assertNotNull(clientApiPowerOfAttorneyDTO.getCards());
		Assert.assertEquals("NL23RABO123456789", clientApiPowerOfAttorneyDTO.getAccount());
	}

	@Test
	public void getDebitCard() throws IOException {
		ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
		ClientApiDebitCardDTO clientApiDebitCardDTO = objectMapper.readValue(currentDebitCardResponse, ClientApiDebitCardDTO.class);

		Assert.assertNotNull(clientApiDebitCardDTO);
		Assert.assertEquals("3333", clientApiDebitCardDTO.getId());
		Assert.assertEquals("Frodo Basggins", clientApiDebitCardDTO.getCardHolder());

		Assert.assertNotNull(clientApiDebitCardDTO.getAtmLimit());
		Assert.assertEquals(Boolean.TRUE, clientApiDebitCardDTO.getContactless());

		Assert.assertNotNull(clientApiDebitCardDTO.getPosLimit());
	}

	@Test
	public void getCreditCard() throws IOException {
		ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
		ClientApiCreditCardDTO clientApiCreditCardDTO = objectMapper.readValue(currentCreditCardResponse, ClientApiCreditCardDTO.class);

		Assert.assertNotNull(clientApiCreditCardDTO);
		Assert.assertEquals("2222", clientApiCreditCardDTO.getId());
		Assert.assertEquals("Boromir", clientApiCreditCardDTO.getCardHolder());

		Assert.assertNotNull(clientApiCreditCardDTO.getCardNumber());
	}

	@Test
	public void getAccountDetails() throws IOException {
		ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
		ClientApiAccountDTO clientApiAccountDTO = objectMapper.readValue(currentAccountResponse, ClientApiAccountDTO.class);

		Assert.assertNotNull(clientApiAccountDTO);
		Assert.assertEquals("uper duper employee", clientApiAccountDTO.getOwner());
		Assert.assertEquals( new BigDecimal(-125), clientApiAccountDTO.getBalance());

		Assert.assertNotNull(clientApiAccountDTO.getCreated());
		Assert.assertEquals("12-10-2007", clientApiAccountDTO.getCreated());

	}
}
