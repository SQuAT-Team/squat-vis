package org.squat_team.vis.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CArchitecture;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CLevel;

import lombok.extern.java.Log;

/**
 * Analyzes the architecture of a {@link CCandidate}. Searches for used
 * components and their dependencies as well as their allocation. The results
 * are then stored in the database.
 */
@Log
public class ArchitectureAnalyzer {
	private ProjectConnector projectConnector;
	private CLevel level;

	/**
	 * Initializes a new analyzer. Each analyzer should only be used for one level.
	 * 
	 * @param level            the level with the candidates to analyze
	 * @param projectConnector identifies the project
	 */
	public ArchitectureAnalyzer(CLevel level, ProjectConnector projectConnector) {
		this.projectConnector = projectConnector;
		this.level = level;
	}

	/**
	 * Runs the analysis.
	 */
	public void analyze() {
		exportArchitectureFiles();
		// TODO: continue
	}

	/**
	 * Exports the byte-formatted architecture files to real files on disk.
	 */
	private void exportArchitectureFiles() {
		for (CCandidate candidate : this.level.getCandidates()) {
			try {
				exportArchitectureFiles(candidate);
			} catch (IOException e) {
				log.log(java.util.logging.Level.SEVERE, "Error while analyzing candidate " + candidate.getCandidateId(),
						e);
			}
		}
	}

	/**
	 * Exports the architecture files of a particular candidate.
	 * 
	 * @param candidate the candidates to consider
	 * @throws IOException
	 */
	private void exportArchitectureFiles(CCandidate candidate) throws IOException {
		// generate temp directory
		String tempDirectoryPrefix = "p" + projectConnector.getProjectId() + "l" + level.getLevelNumber() + "c"
				+ candidate.getCandidateId();
		File tempDirectory = Files.createTempDirectory(tempDirectoryPrefix).toFile();

		// export palladio files to disk
		CArchitecture architecture = candidate.getArchitecture();
		if (candidate.getArchitecture() != null) {
			architecture.setAllocation(exportArchitectureFile(architecture.getAllocationBytes(), tempDirectory,
					architecture.getAllocation().getName()));
			architecture.setRepository(exportArchitectureFile(architecture.getRepositoryBytes(), tempDirectory,
					architecture.getRepository().getName()));
			architecture.setResourceenvironment(exportArchitectureFile(architecture.getResourceenvironmentBytes(),
					tempDirectory, architecture.getResourceenvironment().getName()));
			architecture.setSystem(exportArchitectureFile(architecture.getSystemBytes(), tempDirectory,
					architecture.getSystem().getName()));
			architecture.setUsage(exportArchitectureFile(architecture.getUsageBytes(), tempDirectory,
					architecture.getUsage().getName()));
		}
	}

	/**
	 * Exports a specific file to disk.
	 * 
	 * @param fileData the byte-formatted architecture file
	 * @param directory the directory to export to
	 * @param fileName the name of the file
	 * @return the exported file
	 * @throws IOException
	 */
	private File exportArchitectureFile(byte[] fileData, File directory, String fileName) throws IOException {
		File outputFile = new File(directory, fileName);
		FileOutputStream fileOut = new FileOutputStream(outputFile);
		fileOut.write(fileData);
		fileOut.close();
		return outputFile;
	}

}
