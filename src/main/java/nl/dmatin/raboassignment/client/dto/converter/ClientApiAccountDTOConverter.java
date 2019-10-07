package nl.dmatin.raboassignment.client.dto.converter;

import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.model.dto.AccountDTO;

import org.springframework.core.convert.converter.Converter;

public class ClientApiAccountDTOConverter implements Converter<ClientApiAccountDTO, AccountDTO> {
	@Override
	public AccountDTO convert(ClientApiAccountDTO dto){
		AccountDTO userAccountDto = new AccountDTO();
		userAccountDto.setBalance(dto.getBalance());
		userAccountDto.setCreated(dto.getCreated());
		userAccountDto.setOwner(dto.getOwner());
		return userAccountDto;
	}
}
