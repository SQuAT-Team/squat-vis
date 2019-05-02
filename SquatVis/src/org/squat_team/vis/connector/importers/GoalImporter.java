package org.squat_team.vis.connector.importers;

import java.util.List;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.data.Goal;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Range;

/**
 * Imports {@link CGoal}s and returns {@link Goal} objects, which are then
 * stored in the database.
 */
public class GoalImporter extends AbstractImporter<CGoal, Goal> {
	private GoalDao goalDao;
	private int currentNumberOfGoals = 0;

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public GoalImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public Goal transform(CGoal cgoal) throws InvalidRequestException {
		this.currentNumberOfGoals = 0;
		findDaos();
		Project project = findProject();
		Goal goal = transformAndStoreGoal(cgoal);
		update(project, goal);
		return goal;
	}

	/**
	 * Applies the transformation on the object level and stores the goal and its
	 * children in the database.
	 * 
	 * @param cgoal the goal to transform.
	 * @return the transformed goal. Children are already set.
	 * @throws InvalidRequestException If the input object violates constraints.
	 */
	private Goal transformAndStoreGoal(CGoal cgoal) throws InvalidRequestException {
		Goal goal = new Goal();
		handleSimpleValues(cgoal, goal);
		handleIndex(cgoal, goal);
		handleRange(cgoal, goal);
		handleChildren(cgoal, goal);
		store(goal);
		update(goal.getChildren());
		return goal;
	}

	/**
	 * Sets the primitive values in the goal.
	 * 
	 * @param cgoal values are taken form this object
	 * @param goal  values are set in this object
	 */
	private void handleSimpleValues(CGoal cgoal, Goal goal) {
		goal.setName(cgoal.getName());
		goal.setDescription(cgoal.getDescription());
		goal.setExpectedResponse(cgoal.getExpectedResponse());
	}

	/**
	 * Sets the index of atomic goals.
	 * 
	 * @param cgoal values are taken form this object
	 * @param goal  values are set in this object
	 */
	private void handleIndex(CGoal cgoal, Goal goal) {
		if (cgoal.getChildren().isEmpty()) {
			goal.setIndex(this.currentNumberOfGoals);
			this.currentNumberOfGoals++;
		} else {
			goal.setIndex(-1);
		}
	}

	/**
	 * Recursively imports the children of the imported goals.
	 * 
	 * @param cgoal values are taken form this object
	 * @param goal  values are set in this object
	 * @throws InvalidRequestException If the input object violates constraints.
	 */
	private void handleChildren(CGoal cgoal, Goal goal) throws InvalidRequestException {
		List<Goal> children = goal.getChildren();
		for (CGoal cgoalChild : cgoal.getChildren()) {
			Goal goalChild = transformAndStoreGoal(cgoalChild);
			children.add(goalChild);
			goalChild.setParent(goal);
		}
	}

	/**
	 * Imports the {@link Range} object that belongs to the goal.
	 * 
	 * @param cgoal values are taken form this object
	 * @param goal  values are set in this object
	 * @throws InvalidRequestException If the input object violates constraints.
	 */
	private void handleRange(CGoal cgoal, Goal goal) throws InvalidRequestException {
		RangeImporter rangeImporter = new RangeImporter(connectorService, projectConnector);
		Range range = rangeImporter.transform(cgoal.getRange());
		goal.setRange(range);
	}

	/**
	 * Stores the specified goal in the database.
	 * 
	 * @param goal the goal to store.
	 */
	private void store(Goal goal) {
		goalDao.save(goal);
	}

	/**
	 * Updates the database entry of the specified goal.
	 * 
	 * @param goal the goal to update.
	 */
	private void update(Goal goal) {
		goalDao.update(goal);
	}

	/**
	 * Updates all database entries of the specified goals.
	 * 
	 * @param goal the goals to update.
	 */
	private void update(List<Goal> goals) {
		for (Goal goal : goals) {
			update(goal);
		}
	}

	/**
	 * Sets the required daos in this class.
	 */
	private void findDaos() {
		goalDao = connectorService.getGoalDao();
	}

	/**
	 * Adds the goal to the project.
	 * 
	 * @param project the project to update
	 * @param goal    the goal to set, will replace existing ones
	 */
	private void update(Project project, Goal goal) {
		project.setNumberOfGoals(this.currentNumberOfGoals);
		project.setGoal(goal);
		update(project);
	}

}
