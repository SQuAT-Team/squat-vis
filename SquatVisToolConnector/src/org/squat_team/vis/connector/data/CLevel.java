package org.squat_team.vis.connector.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * A level contains new {@link CCandidate}s and marks the moment the tool
 * expects feedback by the architect. All values have to be set.
 */
@Data
public class CLevel implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -9190778335192222262L;

	private int levelNumber;
	private List<CCandidate> candidates = new ArrayList<>();
}
