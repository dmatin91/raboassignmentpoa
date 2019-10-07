package nl.dmatin.raboassignment.config;

import nl.dmatin.raboassignment.client.dto.converter.ClientApiAccountDTOConverter;
import nl.dmatin.raboassignment.client.dto.converter.ClientApiCreditCardDTOConverter;
import nl.dmatin.raboassignment.client.dto.converter.ClientApiDebitCardDTOConverter;
import nl.dmatin.raboassignment.client.dto.converter.ClientApiPowerOfAttorneyDTOConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ClientApiPowerOfAttorneyDTOConverter());
        registry.addConverter(new ClientApiAccountDTOConverter());
        registry.addConverter(new ClientApiDebitCardDTOConverter());
        registry.addConverter(new ClientApiCreditCardDTOConverter());
    }

}
