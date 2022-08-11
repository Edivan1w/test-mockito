package testes.junit.testesjunit.service.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public UserNotFoundException(String mesagem) {
		super(mesagem);
		
	}

}
