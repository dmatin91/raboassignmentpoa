package nl.dmatin.raboassignment.dto.converter;

import nl.dmatin.raboassignment.dto.UserDataDTO;
import nl.dmatin.raboassignment.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

public class UserModelConverter implements Converter<User, UserDataDTO> {

	@Override
	public UserDataDTO convert(User model) {
		UserDataDTO dto = new UserDataDTO();
		dto.setUsername(model.getUsername());
		dto.setEmail(model.getEmail());
		dto.setPassword(model.getPassword());
		return dto;
	}

	private List<Map<String, Object>> toMaps(List<Document> documents) {
		return documents.stream().map(document -> (Map<String, Object>) document).collect(Collectors.toList());
	}
}
