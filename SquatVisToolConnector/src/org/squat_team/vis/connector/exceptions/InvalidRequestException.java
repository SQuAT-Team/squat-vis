package org.squat_team.vis.connector.exceptions;

public class InvalidRequestException extends Exception {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -8318693933899047936L;

	public InvalidRequestException() {
		super();
	}

	public InvalidRequestException(String text) {
		super(text);
	}

	public InvalidRequestException(String text, Throwable t) {
		super(text, t);
	}
}
