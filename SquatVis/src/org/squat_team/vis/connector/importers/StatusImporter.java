package org.squat_team.vis.connector.importers;

import java.util.Date;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CStatus;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

/**
 * Imports {@link CStatus}s and returns {@link Status} objects, which are then
 * stored in the database.
 */
public class StatusImporter extends AbstractImporter<CStatus, Status> {

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 * 
	 */
	public StatusImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public Status transform(CStatus cstatus) throws InvalidRequestException {
		Project project = findProject();
		Status status = findStatus(project);
		checkStatusUpdateValidity(cstatus, status);
		transformStatus(cstatus, status);
		update(project);
		return status;
	}

	/**
	 * Applies the transformation on the object level.
	 * 
	 * @param cstatus the status to transform
	 * @param status  the transformed status
	 * @return the status
	 */
	private Status transformStatus(CStatus cstatus, Status status) {
		status.setToolProgress(cstatus.getProgress());
		status.setToolMessage(cstatus.getMessage());
		updateTimestamps(status);
		return status;
	}

	/**
	 * Finds the status or creates a new one.
	 * 
	 * @param project the status is part of this project.
	 * @return the found status.
	 */
	private Status findStatus(Project project) {
		Status status = project.getStatus();
		if (status == null) {
			status = new Status();
			project.setStatus(status);
		}
		return status;
	}

	/**
	 * Assures that this update does not reset already made progress.
	 * 
	 * @param cstatus the status to import.
	 * @param status  the current status.
	 * @throws InvalidRequestException if status update is invalid.
	 */
	private void checkStatusUpdateValidity(CStatus cstatus, Status status) throws InvalidRequestException {
		Double newProgress = cstatus.getProgress();
		Double oldProgress = status.getToolProgress();
		if (oldProgress == null) {
			oldProgress = 0d;
		}

		boolean makesProgress = newProgress - oldProgress >= 0;
		boolean fullReset = newProgress == 0.0;
		if (!makesProgress && !fullReset) {
			throw new InvalidRequestException("Request would set the status back to older state");
		}
	}

	/**
	 * Updates the logged times in the status.
	 * 
	 * @param status
	 */
	private void updateTimestamps(Status status) {
		Date now = new Date();
		status.setLastUpdate(now);
	}

}
