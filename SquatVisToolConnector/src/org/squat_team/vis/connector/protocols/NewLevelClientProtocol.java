package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

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
	private boolean noResponse;

	/**
	 * Initializes the protocol.
	 * 
	 * @param level            the level to send. Must not be null!
	 * @param projectConnector the project the level will be pushed to.
	 * @param noResponse       let the server know that no response is expected.
	 */
	public NewLevelClientProtocol(@NonNull CLevel level, @NonNull ProjectConnector projectConnector,
			boolean noResponse) {
		super();
		this.level = level;
		this.projectConnector = projectConnector;
		this.noResponse = noResponse;
	}

	@Override
	protected void sendRequests() throws IOException, ProtocolFailure {
		setCandidateFileByteData();
		Message request = new Message(MessageType.SEND_NEW_LEVEL, projectConnector);
		send(request);
		send(level);
		send(noResponse);
	}

	private void setCandidateFileByteData() throws IOException, ProtocolFailure {
		for (CCandidate candidate : level.getCandidates()) {
			if (candidate.getArchitecture() != null) {
				candidate.getArchitecture().updateByteData();
			}
		}
	}

}
