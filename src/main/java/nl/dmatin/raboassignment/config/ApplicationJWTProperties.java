package nl.dmatin.raboassignment.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt.token")
@Data
public class ApplicationJWTProperties {
	private String secretKey;
	private String expireLength;
}
