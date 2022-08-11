package testes.junit.testesjunit.service.exception;

public class DataIntegratyViolationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataIntegratyViolationException(String mensagem) {
		super(mensagem);
	}

}
