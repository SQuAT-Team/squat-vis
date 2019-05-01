package org.squat_team.vis.data;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This service configures the database at the application's initialization.
 */
@WebListener
public class DatabaseConfigurationService implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContextListener.super.contextInitialized(sce);
		setDatabaseMode();
	}

	private void setDatabaseMode() {
		System.setProperty("objectdb.temp.no-detach", "true");
	}
}
