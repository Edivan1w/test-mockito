package testes.junit.testesjunit.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import testes.junit.testesjunit.model.User;
import testes.junit.testesjunit.model.dto.UserDto;
import testes.junit.testesjunit.service.UserService;

class UserControllerTest {
	
	private static final Long ID = 1l;
	private static final Integer INDEX = 0;
	private static final String NAME = "Valdir";
	private static final String EMAIL = "valdir@mail.com";
	private static final String PASSWORD = "123";
	
	@InjectMocks
	private UserController controller;
	@Mock
	private ModelMapper mapper;
	@Mock
	private UserService userService;
	
	
	
	
	
	private User user;
	private UserDto userDto;
	private URI uri;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
		RequestContextHolder.setRequestAttributes(attributes);
	}

	@Test
	void whenFindByIdThenReturnSuccess() {
		when(userService.findById(anyLong())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDto);
		
		ResponseEntity<UserDto> entity = controller.findById(ID);
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(ResponseEntity.class, entity.getClass());
		assertEquals(UserDto.class, entity.getBody().getClass());
		
		assertEquals(ID, entity.getBody().getId());
		assertEquals(NAME, entity.getBody().getName());
		assertEquals(EMAIL, entity.getBody().getEmail());
		assertEquals(PASSWORD, entity.getBody().getPassword());
	}
	
	@Test
    void whenFindAllThenReturnAListOfUserDTO() {
		when(userService.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(userDto);
		
		ResponseEntity<List<UserDto>> list = controller.findAll();
		
		assertNotNull(list);
		assertEquals(ResponseEntity.class, list.getClass());
		assertEquals(HttpStatus.OK, list.getStatusCode());
		assertNotNull(list.getBody());
		assertEquals(ArrayList.class, list.getBody().getClass());
		assertNotNull(list.getBody().get(0));
		assertEquals(UserDto.class, list.getBody().get(0).getClass());
		
		assertEquals(ID, list.getBody().get(0).getId());
		assertEquals(NAME, list.getBody().get(0).getName());
		assertEquals(EMAIL, list.getBody().get(0).getEmail());
		assertEquals(PASSWORD, list.getBody().get(0).getPassword());
	}
	
	@Test
    void whenCreateThenReturnCreated() {
		when(userService.create(any())).thenReturn(user);
		
		ResponseEntity<UserDto> create = controller.create(userDto);
		
		assertEquals(ResponseEntity.class, create.getClass());
		assertNotNull(create.getHeaders().get("location"));
		assertEquals(HttpStatus.CREATED, create.getStatusCode());
	}
	
	@Test
    void whenUpdateThenReturnSuccess() {
		when(userService.update(any(), anyLong())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDto);
		
		ResponseEntity<UserDto> entity = controller.update(ID, userDto);
		
		assertNotNull(entity);
		assertNotNull(entity.getBody().getClass());
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertEquals(UserDto.class, entity.getBody().getClass());
		assertEquals(ID, entity.getBody().getId());
		assertEquals(NAME, entity.getBody().getName());
		assertEquals(EMAIL, entity.getBody().getEmail());
		assertEquals(PASSWORD, entity.getBody().getPassword());
	}
	
	@Test
    void whenDeleteThenReturnSuccess() {
		doNothing().when(userService).delete(anyLong());
		
		ResponseEntity<?> delete = controller.delete(ID);
		
		assertEquals(ResponseEntity.class, delete.getClass());
		assertEquals(HttpStatus.NO_CONTENT, delete.getStatusCode());
		verify(userService, times(1)).delete(anyLong());
	}

	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
		
		//optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}

}
