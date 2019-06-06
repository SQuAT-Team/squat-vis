package org.squat_team.vis.session;

import java.io.Serializable;

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

	private OptionsInfo optionsInfo;
	private CandidateTagCSSProvider candidateTagMapper = new CandidateTagCSSProvider();

	public TagInfo() {
		// For Serializable
	}

	public TagInfo(OptionsInfo optionsInfo) {
		this.optionsInfo = optionsInfo;
	}

	public String getParetoTag(@NonNull Candidate candidate) {
		if (!optionsInfo.isShowPareto()) {
			return "";
		}
		if (optionsInfo.isUsePopulationPareto() && optionsInfo.isUseRealValuesPareto()) {
			if (candidate.isRealValueParetoPopulationBased()) {
				return candidateTagMapper.getParetoTag();
			}
		} else if (optionsInfo.isUsePopulationPareto() && !optionsInfo.isUseRealValuesPareto()) {
			if (candidate.isUtilityValueParetoPopulationBased()) {
				return candidateTagMapper.getParetoTag();
			}
		} else if (!optionsInfo.isUsePopulationPareto() && optionsInfo.isUseRealValuesPareto()) {
			if (candidate.isRealValueParetoLevelBased()) {
				return candidateTagMapper.getParetoTag();
			}
		} else if (!optionsInfo.isUsePopulationPareto() && !optionsInfo.isUseRealValuesPareto()) {
			if (candidate.isUtilityValueParetoLevelBased()) {
				return candidateTagMapper.getParetoTag();
			}
		}
		return "";
	}

	public String getSuggestionTag(@NonNull Candidate candidate) {
		if (candidate.isSuggested() && optionsInfo.isShowSuggestions()) {
			return candidateTagMapper.getSuggestionTag();
		}
		return "";
	}

}
