package org.squat_team.vis.connector;

import lombok.Getter;
import lombok.NonNull;

/**
 * Defines the server that the protocols should connect to. <br/>
 * <br/>
 * Usually {@link #getDefault()} should be used to connect to the official
 * SquatVis tool or a local instance. Otherwise use
 * {@link #ServerConfiguration(String)} or
 * {@link #ServerConfiguration(String, int)} to connect to other locations.
 */
@Getter
public class ServerConfiguration {
	private static final int DEFAULT_PORT = 4242;
	private static final String DEFAULT_REMOTE_HOST = "www.uni-stuttgart.de/squat/vis";
	private static final String LOCAL_HOST = "localhost";
	private final int port;
	private final String remoteHost;

	/**
	 * Should be used to connect to the official SquatVis tool or a local instance.
	 * 
	 * @return the default configuration to connect to the SquatVis tool.
	 */
	public static ServerConfiguration getDefault() {
		return new ServerConfiguration(DEFAULT_REMOTE_HOST, DEFAULT_PORT);
	}

	/**
	 * Configuration to connect to a custom location that uses the default port.
	 * 
	 * @param remoteHost custom host address.
	 */
	public ServerConfiguration(@NonNull String remoteHost) {
		this(remoteHost, DEFAULT_PORT);
	}

	/**
	 * Configuration to connect to a custom location that uses a custom port.
	 * 
	 * @param remoteHost custom host address
	 * @param port       custom port
	 */
	public ServerConfiguration(String remoteHost, int port) {
		this.remoteHost = remoteHost;
		this.port = port;
	}

	/**
	 * Address of the local host.
	 * 
	 * @return Address of the local host
	 */
	public String getLocalHost() {
		return LOCAL_HOST;
	}

}
