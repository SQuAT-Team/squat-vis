package org.squat_team.vis.connector.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.squat_team.vis.connector.ProjectConnector;

import lombok.Data;

/**
 * An architectural candidate. All values have to be set. <br/>
 * <br/>
 * Note that {@link #setCandidateId(Long)} must be a unique identifier within a
 * {@link ProjectConnector}. <br/>
 * {@link #setRealValues(List)} and {@link #setUtilityValues(List)} must be set
 * in the same order as the {@link CGoal}s in {@link CProject}. Only the
 * {@link CGoal}s without children are considered.
 */
@Data
public class CCandidate implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -8385869869127320909L;

	private Long candidateId;
	private Long parentId;
	private List<Double> realValues = new ArrayList<>();
	private List<Double> utilityValues = new ArrayList<>();
	private boolean isRealValuePareto = false;
	private boolean isUtilityValuePareto = false;
	private boolean isSuggested = false;

}
