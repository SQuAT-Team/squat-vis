package org.squat_team.vis.connector.protocols;

/**
 * Executes tasks after a {@link IServerProtocol} is finished. This is usually
 * additional data analysis.
 */
public interface IPostProtocolHandler {

	/**
	 * Runs the additional tasks that are necessary after the
	 * {@link IServerProtocol} is finished.
	 */
	public void handle();
}
