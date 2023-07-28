package org.squat_team.vis.test.analysis;

import lombok.Data;

/**
 * Contains the architectural model of a single {@link Candidate}.
 */
public class Architecture {
	private Long id;
	private Long projectId;
	private Long candidateId;

	private byte[] repositoryFile;
	private byte[] allocationFile;
	private byte[] systemFile;
	private byte[] resourceenvironmentFile;
	private byte[] usagemodelFile;
}
