package nl.dmatin.raboassignment.client.dto.converter;

import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;
import nl.dmatin.raboassignment.model.dto.PowerOfAttorneyDTO;

import org.springframework.core.convert.converter.Converter;

public class ClientApiPowerOfAttorneyDTOConverter implements Converter<ClientApiPowerOfAttorneyDTO, PowerOfAttorneyDTO> {

	@Override
	public PowerOfAttorneyDTO convert(ClientApiPowerOfAttorneyDTO dto){
		PowerOfAttorneyDTO userDto = new PowerOfAttorneyDTO();
		userDto.setId(dto.getId());
		userDto.setGrantee(dto.getGrantee());
		userDto.setGrantor(dto.getGrantor());
		userDto.setAccount(null);
		userDto.setDirection(dto.getDirection());
		userDto.setCards(null);
		return userDto;
	}
}
