package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CArchitecture;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ArchitectureDao;
import org.squat_team.vis.data.data.Architecture;

/**
 * Imports {@link CArchitecture}s from {@link CCandidate}s and returns {@link Architecture} objects, which are
 * then stored in the database.
 */
public class ArchitectureImporter extends AbstractImporter<CCandidate, Architecture>{

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public ArchitectureImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public Architecture transform(CCandidate candidate) throws InvalidRequestException {
		Architecture architecture = new Architecture();
		setCandidateData(architecture, candidate);
		store(architecture);
		return architecture;
	}
	
	/**
	 * Sets the data of the architecture
	 * 
	 * @param architecture the architecture to write to
	 * @param candidate the candidate that contains the {@link CArchitecture}
	 */
	private void setCandidateData(Architecture architecture, CCandidate candidate) {
		CArchitecture receivedArchitecture = candidate.getArchitecture();
		architecture.setProjectId(projectConnector.getProjectId());
		architecture.setCandidateId(candidate.getCandidateId());
		architecture.setRepositoryFile(receivedArchitecture.getRepositoryBytes());
		architecture.setAllocationFile(receivedArchitecture.getAllocationBytes());
		architecture.setSystemFile(receivedArchitecture.getSystemBytes());
		architecture.setResourceenvironmentFile(receivedArchitecture.getResourceenvironmentBytes());
		architecture.setUsagemodelFile(receivedArchitecture.getUsageBytes());
	}
	
	/**
	 * Stores the architecture in the database.
	 * 
	 * @param architecture the architecture to store
	 */
	private void store(Architecture architecture) {
		ArchitectureDao architectureDao = connectorService.getArchitectureDao();
		architectureDao.save(architecture);
	}

}
