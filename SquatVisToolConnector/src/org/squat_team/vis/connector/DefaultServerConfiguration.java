package org.squat_team.vis.connector;

public class DefaultServerConfiguration {
	private static final DefaultServerConfiguration INSTANCE = new DefaultServerConfiguration();
	private static final int DEFAULT_PORT = 4242;
	private static final String REMOTE_HOST = "www.uni-stuttgart.de/squat/vis";
	private static final String LOCAL_HOST = "localhost";

	public static DefaultServerConfiguration getInstance() {
		return INSTANCE;
	}

	private DefaultServerConfiguration() {
		// SINGLETON
	}

	public int getPort() {
		return DEFAULT_PORT;
	}

	public String getRemoteHost() {
		return REMOTE_HOST;
	}

	public String getLocalHost() {
		return LOCAL_HOST;
	}

}
