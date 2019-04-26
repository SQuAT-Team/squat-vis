package org.squat_team.vis.connector.exceptions;

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
