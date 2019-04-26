package org.squat_team.vis.connector.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CCandidate implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -8385869869127320909L;

	private Long candidateId;
	private Long parentId;
	private List<Double> realValues = new ArrayList<Double>();
	private List<Double> utilityValues = new ArrayList<Double>();
	private boolean isRealValuePareto = false;
	private boolean isUtilityValuePareto = false;

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Double> getRealValues() {
		return realValues;
	}

	public void setRealValues(List<Double> realValues) {
		this.realValues = realValues;
	}

	public List<Double> getUtilityValues() {
		return utilityValues;
	}

	public void setUtilityValues(List<Double> utilityValues) {
		this.utilityValues = utilityValues;
	}

	public boolean isRealValuePareto() {
		return isRealValuePareto;
	}

	public void setRealValuePareto(boolean isRealValuePareto) {
		this.isRealValuePareto = isRealValuePareto;
	}

	public boolean isUtilityValuePareto() {
		return isUtilityValuePareto;
	}

	public void setUtilityValuePareto(boolean isUtilityValuePareto) {
		this.isUtilityValuePareto = isUtilityValuePareto;
	}
}
