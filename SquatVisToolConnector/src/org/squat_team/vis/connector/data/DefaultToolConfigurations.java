package org.squat_team.vis.connector.data;

/**
 * Provides default {@link CToolConfiguration} of some known optimization tools.
 */
public class DefaultToolConfigurations {
	private static final DefaultToolConfigurations INSTANCE = new DefaultToolConfigurations();

	private DefaultToolConfigurations() {
		// Singleton
	}

	public static DefaultToolConfigurations getInstance() {
		return INSTANCE;
	}

	/**
	 * The configuration that is usually used by test clients.
	 */
	public CToolConfiguration getTestDefaultConfiguration() {
		String name = "Default Test Configuration";
		CToolConfiguration configuration = new CToolConfiguration(name);
		configuration.setToolName("Test Client");
		configuration.setHasArchitectures(true);
		configuration.setHasRealValues(true);
		configuration.setHasUtilities(true);
		configuration.setHasParents(true);
		configuration.setSupportSuggestions(true);
		return configuration;
	}

	/**
	 * The configuration that is usually used by SQuAT.
	 */
	public CToolConfiguration getSquatDefaultConfiguration() {
		String name = "Default SQuAT Configuration";
		CToolConfiguration configuration = new CToolConfiguration(name);
		configuration.setToolName("Squat");
		configuration.setHasArchitectures(true);
		configuration.setHasRealValues(true);
		configuration.setHasUtilities(true);
		configuration.setHasParents(true);
		configuration.setSupportSuggestions(true);
		return configuration;
	}

}
