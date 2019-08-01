package org.squat_team.vis.test;

import java.io.File;
import java.io.IOException;

import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.test.importer.IImporter;
import org.squat_team.vis.test.importer.SquatImporter;

public class SquatTestClient extends AbstractTestClient {
	private static final String TXT_PATH = "models" + File.separator + "squat-project" + File.separator
			+ "alternativesRegistry.txt";
	private IImporter importer;
	private int levelStartIndex = 0;
	private int levelEndIndex = 6;

	public SquatTestClient() {
		// Default constructor
		importer = new SquatImporter(TXT_PATH, "");
	}
	
	public SquatTestClient(int levelStartIndex, int levelEndIndex) {
		this.levelStartIndex = levelStartIndex;
		this.levelEndIndex = levelEndIndex;
		String projectNameSuffix = getLevelStartIndex() + "-" + (getLevelEndIndex() - 1);
		importer = new SquatImporter(TXT_PATH, projectNameSuffix);
	}
	
	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
	InvalidRequestException, IOException, InterruptedException {
		(new SquatTestClient()).run();
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
