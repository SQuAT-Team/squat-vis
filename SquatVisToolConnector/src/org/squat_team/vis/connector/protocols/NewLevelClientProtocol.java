package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.data.CLevel;

import lombok.NonNull;

/**
 * A protocol that sends a full level of search to the server. In tools capable
 * of interactive optimization, this is the step before user interaction can
 * take place.<br/>
 * <br/>
 * Another level can be send after this protocol finished.
 */
public class NewLevelClientProtocol extends AbstractSimpleClientProtocol {
	private CLevel level;
	private ProjectConnector projectConnector;

	/**
	 * Initializes the protocol.
	 * 
	 * @param level            the level to send. Must not be null!
	 * @param projectConnector the project the level will be pushed to.
	 */
	public NewLevelClientProtocol(@NonNull CLevel level, ProjectConnector projectConnector) {
		super();
		this.level = level;
		this.projectConnector = projectConnector;
	}

	@Override
	protected void sendRequests() throws IOException {
		Message request = new Message(MessageType.SEND_NEW_LEVEL, projectConnector);
		send(request);
		send(level);
	}

}
