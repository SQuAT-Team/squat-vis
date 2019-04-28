package org.squat_team.vis.connector.importers;

import java.util.Date;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.data.CStatus;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ServerService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class StatusImporter extends AbstractImporter<CStatus, Status> {
	private ProjectDao dao;

	public StatusImporter(ServerService serverService, Connection connection) {
		super(serverService, connection);
	}

	@Override
	public Status transform(CStatus cstatus) throws InvalidRequestException {
		dao = findDao();
		Project project = findProject();
		checkProject(project);
		Status status = findStatus(project);
		checkStatusUpdateValidity(cstatus, status);
		transformStatus(cstatus, status);
		update(project);
		return status;
	}

	private Project findProject() {
		return dao.find(connection.getProjectId());
	}

	private ProjectDao findDao() {
		return serverService.getProjectDao();
	}

	private void checkProject(Project project) throws InvalidRequestException {
		if (project == null) {
			throw new InvalidRequestException("Could not find requested project");
		}
	}

	private Status findStatus(Project project) {
		Status status = project.getStatus();
		if (status == null) {
			status = new Status();
			project.setStatus(status);
		}
		return status;
	}

	private void checkStatusUpdateValidity(CStatus cstatus, Status status) throws InvalidRequestException {
		Double newProgress = cstatus.getProgress();
		Double oldProgress = status.getToolProgress();
		if (oldProgress == null) {
			oldProgress = new Double(0);
		}

		boolean makesProgress = newProgress - oldProgress >= 0;
		boolean fullReset = newProgress == 0.0;
		if (!makesProgress && !fullReset) {
			throw new InvalidRequestException("Request would set the status back to older state");
		}
	}

	private Status transformStatus(CStatus cstatus, Status status) {
		status.setToolProgress(cstatus.getProgress());
		status.setToolMessage(cstatus.getMessage());
		updateTimestamps(status);
		return status;
	}

	private void update(Project project) {
		dao.update(project);
	}

	private void updateTimestamps(Status status) {
		Date now = new Date();
		status.setLastUpdate(now);
	}

}
