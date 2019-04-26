package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.importers.LevelImporter;
import org.squat_team.vis.connector.server.ServerService;

public class NewLevelServerProtocol extends AbstractServerProtocol {
	private CLevel cLevel;

	public NewLevelServerProtocol(ObjectInputStream in, ObjectOutputStream out, ServerService serverService,
			Connection connection) {
		super(in, out, serverService, connection);
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
		(new LevelImporter(serverService, connection)).transform(cLevel);
	}

}
