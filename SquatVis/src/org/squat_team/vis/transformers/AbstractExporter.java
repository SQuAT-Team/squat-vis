package org.squat_team.vis.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

public class AbstractExporter {

	protected List<Level> findActiveLevels(Project project, ProjectInfo projectInfo){
		int numberOfLevels =  projectInfo.getCandidateIdCache().keySet().size();
		Set<Integer> activeLevelIndizes = projectInfo.getLevelInfo().getActiveLevels(numberOfLevels);
		List<Level> activeLevels = new ArrayList<>();
		for (int currentActiveLevelIndex : activeLevelIndizes) {
			activeLevels.add(project.getLevels().get(currentActiveLevelIndex));
		}
		return activeLevels;
	}
	
}
