package org.squat_team.vis.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.squat_team.vis.data.controllers.ArchitectureController;
import org.squat_team.vis.data.daos.ArchitectureDao;
import org.squat_team.vis.data.data.Architecture;
import org.squat_team.vis.export.util.FileZipper;
import org.squat_team.vis.session.SelectorInfo;
import org.squat_team.vis.session.SessionInfo;

import com.google.common.io.Files;

import lombok.extern.java.Log;

/**
 * Allows to download candidate models.
 */
@Log
@Named
@RequestScoped
public class SelectedCandidatesDownloadService {
	@Inject
	private SessionInfo sessionInfo;
	@Inject
	private ArchitectureController architectureController;

	private StreamedContent file;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
	private FileZipper zipper = new FileZipper();

	/**
	 * Exports a specific candidate.
	 * 
	 * @param id the id of the candidate
	 * @return the zipped file
	 */
	public StreamedContent exportCandidate(String id) {
		if (file == null) {
			List<String> selectedCandidateIds = new ArrayList<>();
			selectedCandidateIds.add(id);
			this.file = exportCandidates(selectedCandidateIds);
		}
		return file;
	}

	/**
	 * Exports all the currently (active) selected candidates.
	 * 
	 * @return the zipped file
	 */
	public StreamedContent exportAllSelectedCandidates() {
		if (file == null) {
			Collection<String> selectedCandidateIds = getArchitecturesFromSelected();
			this.file = exportCandidates(selectedCandidateIds);
		}
		return file;
	}
	
	private StreamedContent exportCandidates(Collection<String> selectedCandidateIds) {
		File exportDirectory = getTempDirectory();
		createCandidatesContents(selectedCandidateIds, exportDirectory);
		try {
			File zipFile = zipper.zipChildFiles(exportDirectory);
			InputStream stream = new FileInputStream(zipFile);
			String exportFileName = getExportedFileName(".zip");
			return new DefaultStreamedContent(stream, "application/zip", exportFileName);
		} catch (IOException e) {
			log.log(java.util.logging.Level.WARNING, "Error while zipping directory " + exportDirectory, e);
			return null;
		}
	}
	
	private Collection<String> getArchitecturesFromSelected() {
		SelectorInfo selectorInfo = sessionInfo.getCurrentProjectInfo().getSelectorInfo();
		Set<String> selectedIds = selectorInfo.findOnlyIdsOfActiveLevels(selectorInfo.getSelected());
		return selectedIds;
	}

	private File getTempDirectory() {
		File tempDirectory = Files.createTempDir();
		tempDirectory.deleteOnExit();
		return tempDirectory;
	}

	private File createCandidateDirectory(String candidateId, File parent) {
		File candidateDirectory = new File(parent, "Candidate" + candidateId);
		candidateDirectory.mkdir();
		candidateDirectory.deleteOnExit();
		return candidateDirectory;
	}

	private void createCandidatesContents(Collection<String> candidateIds, File parent) {
		ArchitectureDao architectureDao = architectureController.getService();
		for (String candidateId : candidateIds) {
			Long candidateIdAsLong = Long.parseLong(candidateId);
			Long projectId = getProjectId();
			Architecture architecture = architectureDao.find(projectId, candidateIdAsLong);
			if (architecture != null) {
				File candidateDirectory = createCandidateDirectory(candidateId, parent);
				createCandidateContents(candidateId, architecture, candidateDirectory);
			} else {
				log.log(java.util.logging.Level.WARNING, "Error while exporting candidate " + candidateId);
			}
		}
	}

	private void createCandidateContents(String candidateId, Architecture architecture, File parent) {
		try {
			String namePrefix = "candidate" + candidateId;
			createCandidateFile(namePrefix + ".repository", architecture.getRepositoryFile(), parent);
			createCandidateFile(namePrefix + ".allocation", architecture.getAllocationFile(), parent);
			createCandidateFile(namePrefix + ".system", architecture.getSystemFile(), parent);
			createCandidateFile(namePrefix + ".resourceenvironment", architecture.getResourceenvironmentFile(), parent);
			createCandidateFile(namePrefix + ".usagemodel", architecture.getUsagemodelFile(), parent);
		} catch (IOException e) {
			log.log(java.util.logging.Level.WARNING, "Error while exporting candidate " + candidateId, e);
		}
	}

	private void createCandidateFile(String fileName, byte[] content, File parent) throws IOException {
		File file = new File(parent, fileName);
		file.createNewFile();
		file.deleteOnExit();
		Files.write(content, file);
	}

	private Long getProjectId() {
		return sessionInfo.getProject().getId();
	}

	private String getExportedFileName(String suffix) {
		return "Candidates-" + getFormattedCurrentDate() + suffix;
	}

	private String getFormattedCurrentDate() {
		return dateFormat.format(new Date());
	}


}