package io.github.elishaSword.library.dao;

public class UnsupportedDaoOperationException extends RuntimeException {
	private static final long serialVersionUID = 2004094307611608395L;

	public UnsupportedDaoOperationException(String string) {
		super(string);
	}
}
