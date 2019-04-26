package org.squat_team.vis.connector.exceptions;

public class ConnectionFailure extends Exception {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 1390543675888756857L;

	public ConnectionFailure() {
		super();
	}

	public ConnectionFailure(String text) {
		super(text);
	}

	public ConnectionFailure(String text, Throwable t) {
		super(text, t);
	}
}
