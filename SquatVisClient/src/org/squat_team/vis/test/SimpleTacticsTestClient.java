package org.squat_team.vis.test;

import java.io.IOException;

import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.test.importer.IImporter;
import org.squat_team.vis.test.importer.SimpleTacticsImporter;

public class SimpleTacticsTestClient extends AbstractTestClient {
	private static final SimpleTacticsImporter IMPORTER = new SimpleTacticsImporter(true);
	private static final int LEVELS_START_INDEX = 0;
	private static final int LEVELS_END_INDEX = 2;

	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
	InvalidRequestException, IOException, InterruptedException {
		(new SimpleTacticsTestClient()).mainMethod();
	}
	
	@Override
	protected IImporter getImporter() {
		return IMPORTER;
	}

	@Override
	protected int getLevelStartIndex() {
		return LEVELS_START_INDEX;
	}

	@Override
	protected int getLevelEndIndex() {
		return LEVELS_END_INDEX;
	}
}
