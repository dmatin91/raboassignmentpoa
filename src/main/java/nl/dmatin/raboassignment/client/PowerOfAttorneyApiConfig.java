package nl.dmatin.raboassignment.client;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api.poaapi")
@Getter
@Setter
public class PowerOfAttorneyApiConfig {

	private String uri;
	private String poaService;
	private String debitCardService;
	private String creditCardService;
	private String accountService;
}
