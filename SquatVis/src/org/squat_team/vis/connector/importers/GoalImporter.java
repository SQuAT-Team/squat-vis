package org.squat_team.vis.connector.importers;

import java.util.List;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ServerService;
import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Goal;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Range;

public class GoalImporter extends AbstractImporter<CGoal, Goal> {
	private ProjectDao projectDao;
	private GoalDao goalDao;
	private int currentNumberOfGoals = 0;

	public GoalImporter(ServerService serverService, Connection connection) {
		super(serverService, connection);
	}

	@Override
	public Goal transform(CGoal cgoal) throws InvalidRequestException {
		this.currentNumberOfGoals = 0;
		findDaos();
		Project project = findProject();
		checkProject(project);
		Goal goal = transformGoal(cgoal);
		update(project, goal);
		return goal;
	}

	private Goal transformGoal(CGoal cgoal) throws InvalidRequestException {
		Goal goal = new Goal();
		handleSimpleValues(cgoal, goal);
		handleIndex(cgoal, goal);
		handleRange(cgoal, goal);
		handleChildren(cgoal, goal);
		store(goal);
		update(goal.getChildren());
		return goal;
	}

	private void handleSimpleValues(CGoal cgoal, Goal goal) {
		goal.setName(cgoal.getName());
		goal.setDescription(cgoal.getDescription());
		goal.setExpectedResponse(cgoal.getExpectedResponse());
	}

	private void handleIndex(CGoal cgoal, Goal goal) {
		if (cgoal.getChildren().isEmpty()) {
			goal.setIndex(this.currentNumberOfGoals);
			this.currentNumberOfGoals++;
		} else {
			goal.setIndex(-1);
		}
	}

	private void handleChildren(CGoal cgoal, Goal goal) throws InvalidRequestException {
		List<Goal> children = goal.getChildren();
		for (CGoal cgoalChild : cgoal.getChildren()) {
			Goal goalChild = transformGoal(cgoalChild);
			children.add(goalChild);
			goalChild.setParent(goal);
		}
	}

	private void handleRange(CGoal cgoal, Goal goal) throws InvalidRequestException {
		RangeImporter rangeImporter = new RangeImporter(serverService, connection);
		Range range = rangeImporter.transform(cgoal.getRange());
		goal.setRange(range);
	}

	private void store(Goal goal) {
		goalDao.save(goal);
	}
	
	private void update(Goal goal) {
		goalDao.update(goal);
	}
	
	private void update(List<Goal> goals) {
		for(Goal goal : goals) {
			update(goal);
		}
	}

	private void findDaos() {
		projectDao = serverService.getProjectDao();
		goalDao = serverService.getGoalDao();
	}

	private Project findProject() {
		return projectDao.find(connection.getProjectId());
	}

	private void checkProject(Project project) throws InvalidRequestException {
		if (project == null) {
			throw new InvalidRequestException("Could not find requested project");
		}
	}

	private void update(Project project, Goal goal) {
		project.setNumberOfGoals(this.currentNumberOfGoals);
		project.setGoal(goal);
		projectDao.update(project);
	}

}
