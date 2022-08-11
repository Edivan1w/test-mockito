package testes.junit.testesjunit.controllers.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import testes.junit.testesjunit.service.exception.DataIntegratyViolationException;
import testes.junit.testesjunit.service.exception.UserNotFoundException;

class ExceptionhandlerTest {
	
	@InjectMocks
	private Exceptionhandler exceptionhandler;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
		ResponseEntity<StandardError> entity = exceptionhandler.userNotFounf(
				new UserNotFoundException("Objeto não encontrado"),
				new MockHttpServletRequest());
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
		assertEquals(ResponseEntity.class, entity.getClass());
		assertEquals(StandardError.class, entity.getBody().getClass());
		assertEquals("Objeto não encontrado", entity.getBody().getError());
		assertEquals(404, entity.getBody().getStatus());
		assertNotEquals("/user/2", entity.getBody().getPath());
		assertEquals(LocalDateTime.now(), entity.getBody().getTimestamp());
	}

	@Test
	void testDataIntegratyViolationException() {
		ResponseEntity<StandardError> entity = exceptionhandler.dataIntegratyViolationException(
				new DataIntegratyViolationException("E-mail já cadastrado no sistema"),
				new MockHttpServletRequest());
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
		assertEquals(ResponseEntity.class, entity.getClass());
		assertEquals(StandardError.class, entity.getBody().getClass());
		assertEquals("E-mail já cadastrado no sistema", entity.getBody().getError());
		assertEquals(400, entity.getBody().getStatus());
		assertNotEquals("/user/2", entity.getBody().getPath());
		assertNotEquals(LocalDateTime.now(), entity.getBody().getTimestamp());
	}

}
