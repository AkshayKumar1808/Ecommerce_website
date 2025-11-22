package com.akshay.project.project.exception;

public class RecordNotFoundException extends RuntimeException {

	/**@apiNote
	 * when we throw the object of exception server to client via network. when we
	 * pass the object on the network that class has mention the serialID for
	 * serialization
	 */
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String msg) {
		super(msg);
	}
}
