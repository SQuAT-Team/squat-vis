package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.Set;

import org.squat_team.vis.connector.style.CandidateTagCSSProvider;
import org.squat_team.vis.data.data.Candidate;

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
	private CandidateTagCSSProvider candidateTagMapper = new CandidateTagCSSProvider();

	public TagInfo() {
		// For Serializable
	}

	public TagInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public String getParetoTag(@NonNull Candidate candidate) {
		String paretoTag = "";
		if (candidate.isRealValueParetoPopulationBased()) {
			paretoTag += " " + candidateTagMapper.getParetoRealPopulationTag();
		}
		if (candidate.isUtilityValueParetoPopulationBased()) {
			paretoTag += " " + candidateTagMapper.getParetoUtilityPopulationTag();
		}
		if (candidate.isRealValueParetoLevelBased()) {
			paretoTag += " " + candidateTagMapper.getParetoRealLevelTag();
		}
		if (candidate.isUtilityValueParetoLevelBased()) {
			paretoTag += " " + candidateTagMapper.getParetoUtilityLevelTag();
		}
		return paretoTag;
	}

	public String getSuggestionTag(@NonNull Candidate candidate) {
		if (candidate.isSuggested() && projectInfo.getOptionsInfo().isShowSuggestions()) {
			return candidateTagMapper.getSuggestionTag();
		}
		return "";
	}
	
	public String getInitialTag(Candidate candidate) {
		Set<String> initialLevelIds = projectInfo.getCandidateIdCache().get(0);
		if(initialLevelIds != null && initialLevelIds.contains(candidate.getCandidateId().toString())) {
			return candidateTagMapper.getInitialTag();
		}
		return "";
	}

}
