package org.squat_team.vis.test.importer;

import java.io.IOException;
import java.util.List;

import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;

public interface IImporter {
	public CProject importProject();

	public CToolConfiguration importConfiguration();

	public List<CLevel> importLevels() throws IOException;

	public CGoal importGoals() throws IOException;
}
