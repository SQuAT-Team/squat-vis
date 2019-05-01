package org.squat_team.vis.connector.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This service assures that the {@link ConnectorServer} is shut down after
 * undeploying the application.
 */
@WebListener
public class ConnectorServerShutdownService implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		ServletContextListener.super.contextInitialized(event);
		ConnectorServer server = ConnectorServer.getInstance();
		if (server != null) {
			server.shutdown();
		}
	}

}
