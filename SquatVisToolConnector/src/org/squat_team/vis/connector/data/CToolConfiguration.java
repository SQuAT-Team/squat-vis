package org.squat_team.vis.connector.data;

import java.io.Serializable;

public class CToolConfiguration implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -2493483029789861688L;

	private String name;
	private String toolName;
	private Boolean hasRealValues;
	private Boolean hasArchitectures;
	private Boolean hasUtilities;
	private Boolean hasParents;

	public static CToolConfiguration getTestDefaultConfiguration() {
		String name = "Default Test Configuration";
		CToolConfiguration configuration = new CToolConfiguration(name);
		configuration.setToolName("Test Client");
		configuration.setHasArchitectures(false);
		configuration.setHasRealValues(true);
		configuration.setHasUtilities(true);
		configuration.setHasParents(true);
		return configuration;
	}

	public static CToolConfiguration getSquatDefaultConfiguration() {
		String name = "Default SQuAT Configuration";
		CToolConfiguration configuration = new CToolConfiguration(name);
		configuration.setToolName("Squat");
		configuration.setHasArchitectures(true);
		configuration.setHasRealValues(true);
		configuration.setHasUtilities(true);
		configuration.setHasParents(true);
		return configuration;
	}

	public CToolConfiguration(String name) {
		super();
		this.setName(name);
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasRealValues() {
		return hasRealValues;
	}

	public void setHasRealValues(boolean hasRealValues) {
		this.hasRealValues = hasRealValues;
	}

	public boolean hasArchitectures() {
		return hasArchitectures;
	}

	public void setHasArchitectures(boolean hasArchitectures) {
		this.hasArchitectures = hasArchitectures;
	}

	public Boolean hasUtilities() {
		return hasUtilities;
	}

	public void setHasUtilities(Boolean hasUtilities) {
		this.hasUtilities = hasUtilities;
	}

	public Boolean getHasParents() {
		return hasParents;
	}

	public void setHasParents(Boolean hasParents) {
		this.hasParents = hasParents;
	}
}
