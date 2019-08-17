package org.squat_team.vis.test;

import java.io.IOException;

import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public class AllProjectsTestClient {

	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, IOException, InterruptedException {
		TestDataTestClient.run();
		(new SimpleTacticsTestClient()).run();
		(new SquatTestClient()).run();
	}

}
