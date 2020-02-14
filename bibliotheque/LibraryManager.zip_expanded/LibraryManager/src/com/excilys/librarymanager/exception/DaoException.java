//class pour le lancement des Exceptions: les deux Dao et Service sont implementées à la meme maniere

package com.excilys.librarymanager.exception;

public class DaoException extends Exception{
	
	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

}
