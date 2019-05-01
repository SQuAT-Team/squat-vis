package org.squat_team.vis.connector.exceptions;

import org.squat_team.vis.connector.protocols.IClientProtocol;

/**
 * Thrown when something went wrong with building up a connection during the run
 * of a {@link IClientProtocol}.
 */
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
