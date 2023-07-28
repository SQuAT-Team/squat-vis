package org.squat_team.vis.test.analysis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Represents an architectural candidate. This can be the initial candidate or
 * candidates that are the result of an optimization process.
 */
@Data
public class Candidate {
	private Long id;

	private Long projectId;
	private Long candidateId;
	private Candidate parent;
	private List<Double> realValues = new ArrayList<>();
	private List<Double> utilityValues = new ArrayList<>();
	private boolean isRealValueParetoLevelBased = false;
	private boolean isUtilityValueParetoLevelBased = false;
	private boolean isRealValueParetoPopulationBased = false;
	private boolean isUtilityValueParetoPopulationBased = false;
	private boolean isSuggested = false;
	private ArchitectureAnalysisData staticArchitectureAnalysisData;
}
