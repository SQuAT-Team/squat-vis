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
	/** Notifies that the project should be marked as terminated */
	SEND_PROJECT_TERMINATED,
	/** A request to send the (human selected) candidates for the next level */
	REQUEST_LEVEL_RESPONSE,
	/** An answer that states that the receiver accepted the request */
	ACCEPT,
	/** An answer that states that the receiver declined the request */
	DECLINE;
}
