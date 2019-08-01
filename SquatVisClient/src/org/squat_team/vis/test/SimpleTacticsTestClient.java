package org.squat_team.vis.test;

import java.io.IOException;

import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.test.importer.IImporter;
import org.squat_team.vis.test.importer.SimpleTacticsImporter;

public class SimpleTacticsTestClient extends AbstractTestClient {
	private IImporter importer;
	private int levelStartIndex = 0;
	private int levelEndIndex = 3;

	public SimpleTacticsTestClient() {
		// Default constructor
		importer = new SimpleTacticsImporter(true, "");
	}
	
	public SimpleTacticsTestClient(int levelStartIndex, int levelEndIndex) {
		this.levelStartIndex = levelStartIndex;
		this.levelEndIndex = levelEndIndex;
		String projectNameSuffix = getLevelStartIndex() + "-" + (getLevelEndIndex() - 1);
		importer = new SimpleTacticsImporter(true, projectNameSuffix);
	}
	
	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
	InvalidRequestException, IOException, InterruptedException {
		(new SimpleTacticsTestClient()).run();
	}
	
	@Override
	protected IImporter getImporter() {
		return importer;
	}

	@Override
	protected int getLevelStartIndex() {
		return levelStartIndex;
	}

	@Override
	protected int getLevelEndIndex() {
		return levelEndIndex;
	}
}
