package org.squat_team.vis.connector;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 6041040934465134021L;

	private MessageType type;
	private Connection connection;
	private Exception exception;

	public Message(MessageType type, Connection connection) {
		super();
		this.setType(type);
		this.setConnection(connection);
	}

	public MessageType getType() {
		return type;
	}

	private void setType(MessageType type) {
		this.type = type;
	}

	public Connection getConnection() {
		return connection;
	}

	private void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
