package org.squat_team.vis.connector.protocols;

public class EmptyPostProtocolHandler extends AbstractPostProtocolHandler{

	public EmptyPostProtocolHandler() {
		super(null, null);
	}

	@Override
	public void handle() {
		// do nothing
	}

	
}
