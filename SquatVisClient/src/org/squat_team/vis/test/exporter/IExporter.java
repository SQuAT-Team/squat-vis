package org.squat_team.vis.test.exporter;

import java.io.IOException;
import java.util.List;

import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;

/**
 * Exports the project data to another format, so it can be imported by other
 * tools for comparison.
 */
public interface IExporter {
	public void export(CProject project, CGoal goal, List<CLevel> levels, CToolConfiguration toolConfiguration)
			throws IOException;
}
