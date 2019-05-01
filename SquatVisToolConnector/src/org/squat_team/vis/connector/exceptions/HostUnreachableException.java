package org.squat_team.vis.connector.exceptions;

import org.squat_team.vis.connector.ServerConfiguration;

/**
 * More specific than {@link ConnectionFailure}. Is thrown when the server
 * specified by {@link ServerConfiguration} can not be found. This indicates
 * that the configuration is wrong or the server is down.
 */
public class HostUnreachableException extends ConnectionFailure {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -4825664775667796978L;

	public HostUnreachableException() {
		super();
	}

	public HostUnreachableException(String text) {
		super(text);
	}

	public HostUnreachableException(String text, Throwable t) {
		super(text, t);
	}
}
