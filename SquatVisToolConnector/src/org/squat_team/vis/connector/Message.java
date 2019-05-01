package org.squat_team.vis.connector;

import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

/**
 * Messages are send between the server and client as wrapper for information.
 * The messages is described in more detail by its {@link MessageType}.
 */
@Data
public class Message implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 6041040934465134021L;

	private MessageType type;
	private ProjectConnector projectConnector;
	private Exception exception;

	/**
	 * Creates a new message.
	 * 
	 * @param type             Must not be null.
	 * @param projectConnector Informs the receiver that the request is associated
	 *                         to the specified project.
	 */
	public Message(@NonNull MessageType type, ProjectConnector projectConnector) {
		super();
		this.setType(type);
		this.setProjectConnector(projectConnector);
	}

}
