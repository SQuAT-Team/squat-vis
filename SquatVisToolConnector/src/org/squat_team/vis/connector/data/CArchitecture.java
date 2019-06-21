package org.squat_team.vis.connector.data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

import lombok.Data;

@Data
public class CArchitecture implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -70809038419674839L;

	private File repository;
	private File allocation;
	private File system;
	private File resourceenvironment;
	private File usage;

	private byte[] repositoryBytes;
	private byte[] allocationBytes;
	private byte[] systemBytes;
	private byte[] resourceenvironmentBytes;
	private byte[] usageBytes;

	public void updateByteData() throws IOException {
		allocationBytes = Files.readAllBytes(allocation.toPath());
		repositoryBytes = Files.readAllBytes(repository.toPath());
		resourceenvironmentBytes = Files.readAllBytes(resourceenvironment.toPath());
		systemBytes = Files.readAllBytes(system.toPath());
		usageBytes = Files.readAllBytes(usage.toPath());
	}
}
