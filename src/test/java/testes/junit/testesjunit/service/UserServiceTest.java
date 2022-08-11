package testes.junit.testesjunit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import testes.junit.testesjunit.model.User;
import testes.junit.testesjunit.model.dto.UserDto;
import testes.junit.testesjunit.repositories.UserRepository;
import testes.junit.testesjunit.service.exception.DataIntegratyViolationException;
import testes.junit.testesjunit.service.exception.UserNotFoundException;
import testes.junit.testesjunit.service.util.UserUtil;

class UserServiceTest {

	private static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";
	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
	private static final Long ID = 1l;
	private static final Integer INDEX = 0;
	private static final String NAME = "Valdir";
	private static final String EMAIL = "valdir@mail.com";
	private static final String PASSWORD = "123";

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;

	@Mock
	private ModelMapper mapper;
	
	@Mock
	private UserUtil userUtil;

	private User user;
	private UserDto userDto;
	private Optional<User> optionalUser;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		when(repository.findById(anyLong())).thenReturn(optionalUser);

		User user = service.findById(ID);
		assertNotNull(user);
		assertEquals(User.class, user.getClass());
		assertEquals(ID, user.getId());
		assertEquals(NAME, user.getName());
		assertEquals(EMAIL, user.getEmail());
		assertEquals(PASSWORD, user.getPassword());
	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyLong()))
		.thenThrow(new UserNotFoundException(OBJETO_NAO_ENCONTRADO));
		
		try {
			service.findById(ID);
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(repository.findAll()).thenReturn(List.of(user));
		
		List<User> list = service.findAll();
		
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals(User.class, list.get(0).getClass());
		assertEquals(ID, list.get(0).getId());
		assertEquals(NAME, list.get(0).getName());
		assertEquals(EMAIL, list.get(0).getEmail());
		assertEquals(PASSWORD, list.get(0).getPassword());
		
	}

	@Test
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		
		User u = service.create(userDto);
		
		assertNotNull(u);
		assertEquals(User.class, u.getClass());
		assertEquals(ID, u.getId());
		assertEquals(NAME, u.getName());
		assertEquals(EMAIL, u.getEmail());
		assertEquals(PASSWORD, u.getPassword());
		
	}
	
	@Test
	void whenCreateThenReturnAnDataIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2l);
			service.create(userDto);
		} catch (Exception e) {
			assertEquals(DataIntegratyViolationException.class, e.getClass());
			assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, e.getMessage());
		}
	}
	
	

	@Test
	void whenUpdateThenReturnSuccess() {
		when(repository.findById(anyLong())).thenReturn(optionalUser);
		when(userUtil.atualizar(any(), any())).thenReturn(user);
		
		User u = service.update(userDto, ID);
		
		assertNotNull(u);
		assertEquals(User.class, u.getClass());
		assertEquals(ID, u.getId());
		assertEquals(NAME, u.getName());
		assertEquals(EMAIL, u.getEmail());
		assertEquals(PASSWORD, u.getPassword());
		
	}
	@Test
	void whenUpdateThenReturnAnDataIntegrityViolationException() {

		when(repository.findByEmail(anyString())).thenThrow(
				new DataIntegratyViolationException(E_MAIL_JA_CADASTRADO_NO_SISTEMA));
		try {
			service.update(userDto, ID);
		} catch (Exception e) {
			assertEquals(DataIntegratyViolationException.class, e.getClass());
			assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, e.getMessage());
		}
	}

	@Test
	void deletewithSuccess() {
		when(repository.findById(anyLong())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(anyLong());
		
		service.delete(ID);
		
		verify(repository, times(1)).deleteById(anyLong());
		
	}
	
	@Test
	void deletewithNotSuccess() {
		when(repository.findById(anyLong())).thenThrow(
				new UserNotFoundException(OBJETO_NAO_ENCONTRADO));
		
		try {
			service.delete(ID);
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
		}
		
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}

}
