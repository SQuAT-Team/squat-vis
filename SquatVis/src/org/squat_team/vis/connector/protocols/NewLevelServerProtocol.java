package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.importers.LevelImporter;
import org.squat_team.vis.connector.server.ConnectorService;

public class NewLevelServerProtocol extends AbstractServerProtocol {
	private CLevel cLevel;

	public NewLevelServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService,
			ProjectConnector connection) {
		super(in, out, connectorService, connection);
	}

	@Override
	public void execute() throws ProtocolFailure, IOException {
		try {
			receive();
			transform();
			respondAccept(connection);
		} catch (InvalidRequestException e) {
			respondDeclineWithException(connection, e);
		}
	}

	private void receive() throws ProtocolFailure, IOException, InvalidRequestException {
		cLevel = receive(CLevel.class);
	}

	private void transform() throws InvalidRequestException {
		(new LevelImporter(connectorService, connection)).transform(cLevel);
	}

	@Override
	public IPostProtocolHandler getPostProtocolHandler() {
		return new NewLevelPostProtocolHandler(connectorService, connection);
	}

}
