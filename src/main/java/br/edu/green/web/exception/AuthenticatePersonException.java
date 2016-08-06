package br.edu.green.web.exception;

public class AuthenticatePersonException extends Exception {

	private static final long serialVersionUID = 8677278613364579624L;

	public AuthenticatePersonException() {
		super();
	}

	public AuthenticatePersonException(int status) {
		super(Integer.toString(status));
	}

	public AuthenticatePersonException(String message) {
		super(message);
	}

	public AuthenticatePersonException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticatePersonException(Throwable cause) {
		super(cause);
	}
}
