package org.squat_team.vis.data.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Contains the architectural model of a single {@link Candidate}.
 */
@Entity
@Data
public class Architecture {
	@Id
	@GeneratedValue
	private Long id;
	private Long projectId;
	private Long candidateId;

	private byte[] repositoryFile;
	private byte[] allocationFile;
	private byte[] systemFile;
	private byte[] resourceenvironmentFile;
	private byte[] usagemodelFile;
}
