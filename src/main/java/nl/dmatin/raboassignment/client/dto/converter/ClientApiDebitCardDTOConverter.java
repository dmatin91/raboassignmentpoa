package nl.dmatin.raboassignment.client.dto.converter;

import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.model.CardType;
import nl.dmatin.raboassignment.model.dto.DebitCardDTO;

import org.springframework.core.convert.converter.Converter;

public class ClientApiDebitCardDTOConverter implements Converter<ClientApiDebitCardDTO, DebitCardDTO> {

	@Override
	public DebitCardDTO convert(ClientApiDebitCardDTO dto){
		DebitCardDTO userDebitCardDto = new DebitCardDTO();
		userDebitCardDto.setCardHolder(dto.getCardHolder());
		userDebitCardDto.setCardNumber(dto.getCardNumber());
		userDebitCardDto.setContactless(dto.getContactless());
		userDebitCardDto.setAtmLimit(dto.getAtmLimit());
		userDebitCardDto.setPosLimit(dto.getPosLimit());
		userDebitCardDto.setSequenceNumber(dto.getSequenceNumber());
		userDebitCardDto.setType(CardType.DEBIT_CARD);
		return userDebitCardDto;
	}
}
