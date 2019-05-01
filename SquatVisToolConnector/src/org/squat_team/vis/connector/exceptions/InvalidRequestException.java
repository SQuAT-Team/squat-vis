package org.squat_team.vis.connector.exceptions;

/**
 * Is thrown if a server side exception occurred an the server ends the protocol
 * unfinished. This indicates that something is wrong with the send data, e.g.,
 * variables not set or constraint violations.
 */
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
