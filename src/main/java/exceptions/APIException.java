package exceptions;

public class APIException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public APIException() {
		// TODO Auto-generated constructor stub
	}

	public APIException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public APIException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public APIException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public APIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
