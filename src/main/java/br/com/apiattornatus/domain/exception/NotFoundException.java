package br.com.apiattornatus.domain.exception;

import br.com.apiattornatus.messages.Messages;

public class NotFoundException extends Exception{
	
	private static final long serialVersionUID = -7036484399498314743L;

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException() {
		super(Messages.NOT_FOUND);
	}
}
