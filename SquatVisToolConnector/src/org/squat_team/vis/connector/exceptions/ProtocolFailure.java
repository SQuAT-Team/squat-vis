package org.squat_team.vis.connector.exceptions;

/**
 * Thrown when something unexpected happened that does not correspond to the
 * protocol. Reasons can be a bug in the protocol or a version mismatch between
 * server and client side protocols.
 */
public class ProtocolFailure extends Exception {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 1194862033662119722L;

	public ProtocolFailure() {
		super();
	}

	public ProtocolFailure(String text) {
		super(text);
	}

	public ProtocolFailure(String text, Throwable t) {
		super(text, t);
	}
}
