package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.squat_team.vis.connector.style.CandidateSelectorCSSProvider;
import org.squat_team.vis.data.data.Candidate;

import lombok.Data;

/**
 * Responsible for storing and providing the selector information of the
 * candidates.
 */
@Data
public class SelectorInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -6415382161054942298L;
	private ProjectInfo projectInfo;
	private CandidateSelectorCSSProvider candidateSelectorMapper = new CandidateSelectorCSSProvider();
	private Set<String> selected = new HashSet<>();
	private Set<String> marked = new HashSet<>();
	private Set<String> current = new HashSet<>();
	private Set<String> comparison = new HashSet<>();

	public SelectorInfo(ProjectInfo projectInfo, List<Candidate> candidates) {
		this.projectInfo = projectInfo;
		addSuggestedCandidatesAsSelected(candidates);
	}
	
	private void addSuggestedCandidatesAsSelected(List<Candidate> candidates) {
		for(Candidate candidate : candidates) {
			if(candidate.isSuggested()) {
				selected.add(candidate.getCandidateId().toString());
			}
		}
	}
	
	public void addSelected() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		selected.add(id);
	}

	public void removeSelected() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		selected.remove(id);
	}

	public void removeAllSelected() {
		selected.clear();
	}

	public boolean isSelected(String id) {
		return selected.contains(id);
	}

	public boolean isMarked(String id) {
		return marked.contains(id);
	}

	public boolean isComparison(String id) {
		return comparison.contains(id);
	}

	public boolean isCurrent(String id) {
		return current.contains(id);
	}

	public int getNumberOfSelected() {
		return findOnlyIdsOfActiveLevels(selected).size();
	}

	public int getNumberOfMarked() {
		return findOnlyIdsOfActiveLevels(marked).size();
	}

	public int getNumberOfComparison() {
		return findOnlyIdsOfActiveLevels(comparison).size();
	}

	public int getNumberOfCurrent() {
		return findOnlyIdsOfActiveLevels(current).size();
	}
	
	public int getNumberOfAll() {
		return findOnlyIdsOfActiveLevels(null).size();
	}
	
	private Set<String> findOnlyIdsOfActiveLevels(Set<String> candidateIdstoCompareWith) {
		boolean setToCompareWithSpecified = (candidateIdstoCompareWith != null);
		Map<Integer, Set<String>> candidateIdCache = projectInfo.getCandidateIdCache();
		Set<Integer> activeLevelIndezes = projectInfo.getLevelInfo().getActiveLevels(candidateIdCache.keySet().size());
		Set<String> allActiveLevelSelectedCandidates = new HashSet<>();
		for(int activeLevelIndex : activeLevelIndezes) {
			Set<String> candidateIdsInCurrentLevel = candidateIdCache.get(activeLevelIndex);
			Set<String> candidateIdsInCurrentLevelCopy = new HashSet<>(candidateIdsInCurrentLevel);
			if(setToCompareWithSpecified) {
				candidateIdsInCurrentLevelCopy.retainAll(candidateIdstoCompareWith);
			}
			allActiveLevelSelectedCandidates.addAll(candidateIdsInCurrentLevelCopy);
		}
		return allActiveLevelSelectedCandidates;
	}	

	public String getCandidateStarType(String id) {
		return candidateSelectorMapper.getStarType(selected.contains(id));
	}

	public String getSelectorsType(String id) {
		String selectors = "";
		if (current.contains(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getCurrentStyle();
		}
		if (marked.contains(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getMarkedStyle();
		}
		if (selected.contains(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getSelectedStyle();
		}
		if (comparison.contains(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getComparisonStyle();
		}
		return selectors;
	}

	public void setSelectorCurrent() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		current.add(id);
	}

	public void resetSelectorCurrent() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		current.remove(id);
	}

	public void setSelectorComparison() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		comparison.add(id);
	}

	public void resetSelectorComparison() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		comparison.remove(id);
	}

	public void setSelectorMarked() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		selected.remove(id);
		marked.add(id);
	}

	public void resetSelectorMarked() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		marked.remove(id);
	}

	public void setSelectorSelected() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		marked.remove(id);
		selected.add(id);
	}

	public void resetSelectorSelected() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		if (id == null) {
			return;
		}
		selected.remove(id);
	}

	private List<String> extractIdsFromString(String ids) {
		return Arrays.asList(ids.split(","));
	}
	
	public void setAllSelectorCurrent() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		current.addAll(extractIdsFromString(ids));
	}

	public void resetAllSelectorCurrent() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		current.removeAll(extractIdsFromString(ids));
	}

	public void setAllSelectorComparison() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		comparison.addAll(extractIdsFromString(ids));
	}

	public void resetAllSelectorComparison() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		comparison.removeAll(extractIdsFromString(ids));
	}

	public void setAllSelectorMarked() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		List<String> idsAsList = extractIdsFromString(ids);
		selected.removeAll(idsAsList);
		marked.addAll(idsAsList);
	}

	public void resetAllSelectorMarked() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		marked.removeAll(extractIdsFromString(ids));
	}

	public void setAllSelectorSelected() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		List<String> idsAsList = extractIdsFromString(ids);
		marked.removeAll(idsAsList);
		selected.addAll(idsAsList);
		}

	public void resetAllSelectorSelected() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		selected.removeAll(extractIdsFromString(ids));
	}

	public void selectorLevelUp() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		selectorLevelUp(id);
	}

	private void selectorLevelUp(String id) {
		if (id == null) {
			return;
		}
		if (marked.contains(id)) {
			marked.remove(id);
			selected.add(id);
		} else if (selected.contains(id)) {
			selected.remove(id);
		} else {
			marked.add(id);
		}
	}

	public void selectorLevelUpAll() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		for(String id : extractIdsFromString(ids)) {
			selectorLevelUp(id);
		}
	}

	public void selectorClearCurrent() {
		current.clear();
	}

	public void selectorClearComparison() {
		comparison.clear();
	}

	public void selectorClearMarked() {
		marked.clear();
	}

	public void selectorClearSelected() {
		selected.clear();
	}

	public void selectorMarkAllCurrent() {
		for (String currentId : current) {
			if (!selected.contains(currentId)) {
				marked.add(currentId);
			}
		}
	}

	public void selectorSelectAllComparison() {
		selected.addAll(comparison);
		marked.removeAll(comparison);
	}

	public void selectorSelectAllMarked() {
		selected.addAll(marked);
		marked.clear();
	}

	public void selectorExportAllSelected() {
	}

}
