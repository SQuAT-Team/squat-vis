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
		(new SimpleTacticsTestClient(0,2)).run();
		(new SimpleTacticsTestClient(2,3)).run();
		
		(new SquatTestClient()).run();
		(new SquatTestClient(0,1)).run();
		(new SquatTestClient(1,2)).run();
		(new SquatTestClient(2,3)).run();
		(new SquatTestClient(3,4)).run();
		(new SquatTestClient(4,5)).run();
		(new SquatTestClient(5,6)).run();
	}

}
