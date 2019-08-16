package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.Set;

import org.squat_team.vis.connector.style.CandidateTagCSSProvider;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.session.OptionsInfo.ParetoMode;

import lombok.Data;
import lombok.NonNull;

/**
 * Provides session-based information about the tags of candidates.
 */
@Data
public class TagInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -4728243877557516574L;

	private ProjectInfo projectInfo;
	private OptionsInfo optionsInfo;
	private CandidateTagCSSProvider candidateTagMapper = new CandidateTagCSSProvider();

	public TagInfo() {
		// For Serializable
	}

	public TagInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
		this.optionsInfo = projectInfo.getOptionsInfo();
	}

	public String getParetoTag(@NonNull Candidate candidate) {
		String paretoTag = "";
		if (isParetoMode(ParetoMode.REAL_VALUE_POPULATION_PARETO) && candidate.isRealValueParetoPopulationBased()) {
			return candidateTagMapper.getParetoTag();
		}
		if (isParetoMode(ParetoMode.UTILITY_POPULATION_PARETO) && candidate.isUtilityValueParetoPopulationBased()) {
			return candidateTagMapper.getParetoTag();
		}
		if (isParetoMode(ParetoMode.REAL_VALUE_LEVEL_PARETO) && candidate.isRealValueParetoLevelBased()) {
			return candidateTagMapper.getParetoTag();
		}
		if (isParetoMode(ParetoMode.UTILITY_LEVEL_PARETO) && candidate.isUtilityValueParetoLevelBased()) {
			return candidateTagMapper.getParetoTag();
		}
		return paretoTag;
	}

	public String getSuggestionTag(@NonNull Candidate candidate) {
		if (candidate.isSuggested() && optionsInfo.isShowSuggestions()) {
			return candidateTagMapper.getSuggestionTag();
		}
		return "";
	}

	public String getInitialTag(Candidate candidate) {
		Set<String> initialLevelIds = projectInfo.getCandidateIdCache().get(0);
		if (initialLevelIds != null && initialLevelIds.contains(candidate.getCandidateId().toString())) {
			return candidateTagMapper.getInitialTag();
		}
		return "";
	}

	private boolean isParetoMode(ParetoMode paretoMode) {
		return optionsInfo.getParetoMode() == paretoMode;
	}

}
