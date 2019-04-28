package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public interface IServerProtocol {
	public void execute() throws ProtocolFailure, IOException, InvalidRequestException;
	public IPostProtocolHandler getPostProtocolHandler();
}
