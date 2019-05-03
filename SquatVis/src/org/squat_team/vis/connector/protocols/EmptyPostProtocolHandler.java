package org.squat_team.vis.connector.protocols;

/**
 * The null-version of the {@link AbstractPostProtocolHandler}
 */
public class EmptyPostProtocolHandler extends AbstractPostProtocolHandler {

	public EmptyPostProtocolHandler() {
		super(null, null);
	}

	@Override
	public void handle() {
		// do nothing
	}

}
