package testes.junit.testesjunit.service.util;

import testes.junit.testesjunit.model.User;
import testes.junit.testesjunit.model.dto.UserDto;
import testes.junit.testesjunit.repositories.UserRepository;

public class UserUtil {

	public  User atualizar(User user, UserDto dto) {
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}
	
	

}
