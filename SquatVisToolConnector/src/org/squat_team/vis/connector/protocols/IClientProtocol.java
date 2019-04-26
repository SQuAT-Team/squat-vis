package org.squat_team.vis.connector.protocols;

import java.util.concurrent.Callable;

import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public interface IClientProtocol<T> extends Callable<T> {
	@Override
	public T call() throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException;
}
