package org.squat_team.vis.util;

import java.util.Objects;

import org.squat_team.vis.data.data.Level;

/**
 * Identifies to which {@link Project} and {@link Level} a response belongs to.
 */
public class ResponseIdentifier {
	private final long projectId;
	private final int levelNumber;

	/**
	 * Creates a new identifier. An identifier with the same parameters is assumed
	 * equal to another.
	 * 
	 * @param projectId   identifies the project.
	 * @param levelNumber the index of the level in the project.
	 */
	public ResponseIdentifier(long projectId, int levelNumber) {
		this.projectId = projectId;
		this.levelNumber = levelNumber;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ResponseIdentifier) {
			ResponseIdentifier otherIdentifier = (ResponseIdentifier) object;
			return this.projectId == otherIdentifier.projectId && this.levelNumber == otherIdentifier.levelNumber;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(projectId, levelNumber);
	}
}