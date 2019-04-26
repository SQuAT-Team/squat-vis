package org.squat_team.vis.connector.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CLevel implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -9190778335192222262L;

	private List<CCandidate> candidates = new ArrayList<CCandidate>();

	public List<CCandidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<CCandidate> candidates) {
		this.candidates = candidates;
	}

}
