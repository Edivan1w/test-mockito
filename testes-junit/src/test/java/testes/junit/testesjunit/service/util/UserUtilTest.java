package testes.junit.testesjunit.service.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import testes.junit.testesjunit.model.User;
import testes.junit.testesjunit.model.dto.UserDto;

class UserUtilTest {
	
	private static final Long ID = 1l;
	private static final Integer INDEX = 0;
	private static final String NAME = "Valdir";
	private static final String EMAIL = "valdir@mail.com";
	private static final String PASSWORD = "123";
	
	
	private User user;
	private UserDto userDto;
	
	@InjectMocks
	private UserUtil userUtil;
	
	@BeforeEach
	void start() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void atualizarTest() {
		User user2 = userUtil.atualizar(user, userDto);
		
		assertNotNull(user2);
		assertEquals(User.class, user2.getClass());
		assertEquals(ID, user2.getId());
		assertEquals(NAME, user2.getName());
		assertEquals(EMAIL, user2.getEmail());
		assertEquals(PASSWORD, user2.getPassword());
	}
	
	
	
	
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
		//optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}

}
