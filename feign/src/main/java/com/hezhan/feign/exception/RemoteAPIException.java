package com.hezhan.feign.exception;

public class RemoteAPIException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteAPIException() {
    }

    public RemoteAPIException(String message) {
        super(message);
    }

    public RemoteAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
