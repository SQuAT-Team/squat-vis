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

public class NewLevelServerProtocol extends AbstractSimpleServerProtocol {
	private CLevel cLevel;

	public NewLevelServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService,
			ProjectConnector projectConnector) {
		super(in, out, connectorService, projectConnector);
	}

	@Override
	protected void receive() throws ProtocolFailure, IOException, InvalidRequestException {
		cLevel = receive(CLevel.class);
	}

	@Override
	protected void transform() throws InvalidRequestException {
		(new LevelImporter(connectorService, projectConnector)).transform(cLevel);
	}

	@Override
	public IPostProtocolHandler getPostProtocolHandler() {
		return new NewLevelPostProtocolHandler(connectorService, projectConnector);
	}

}
