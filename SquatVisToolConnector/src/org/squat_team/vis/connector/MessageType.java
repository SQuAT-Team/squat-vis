package org.squat_team.vis.connector;

/**
 * Describes the purpose of a {@link Message}.
 */
public enum MessageType {
	/** A request to create a new project */
	REQUEST_NEW_PROJECT,
	/** A request to update the status of the optimization in the level */
	SEND_STATUS_UPDATE,
	/** A request to send a finished level */
	SEND_NEW_LEVEL,
	/** An answer that states that the receiver accepted the request */
	ACCEPT,
	/** An answer that states that the receiver declined the request */
	DECLINE;
}
