package nl.dmatin.raboassignment.client.dto.converter;

import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.model.dto.CreditCardDTO;

import org.springframework.core.convert.converter.Converter;

public class ClientApiCreditCardDTOConverter implements Converter<ClientApiCreditCardDTO, CreditCardDTO> {
	@Override
	public CreditCardDTO convert(ClientApiCreditCardDTO dto){
		CreditCardDTO userCreditCardDto = new CreditCardDTO();
		userCreditCardDto.setCardHolder(dto.getCardHolder());
		userCreditCardDto.setCardNumber(dto.getCardNumber());
		userCreditCardDto.setSequenceNumber(dto.getSequenceNumber());
		userCreditCardDto.setMonthlyLimit(dto.getMonthlyLimit());
		return userCreditCardDto;
	}
}
