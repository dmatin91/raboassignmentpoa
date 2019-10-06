package nl.dmatin.raboassignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApplicationJWTProperties.class)
public class AdditionalConfiguration {
	@Autowired
	private ApplicationJWTProperties additionalProperties;
}
