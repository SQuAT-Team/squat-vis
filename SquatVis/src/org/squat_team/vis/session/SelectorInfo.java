package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.squat_team.vis.connector.style.CandidateSelectorCSSProvider;

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
	private CandidateSelectorCSSProvider candidateSelectorMapper = new CandidateSelectorCSSProvider();
	private Set<String> selected = new HashSet<>();
	private Set<String> marked = new HashSet<>();
	private Set<String> current = new HashSet<>();
	private Set<String> comparison = new HashSet<>();

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
		return selected.size();
	}

	public int getNumberOfMarked() {
		return marked.size();
	}

	public int getNumberOfComparison() {
		return comparison.size();
	}

	public int getNumberOfCurrent() {
		return current.size();
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

	public void setAllSelectorCurrent() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
	}

	public void resetAllSelectorCurrent() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
	}

	public void setAllSelectorComparison() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
	}

	public void resetAllSelectorComparison() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
	}

	public void setAllSelectorMarked() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
	}

	public void resetAllSelectorMarked() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
	}

	public void setAllSelectorSelected() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
	}

	public void resetAllSelectorSelected() {
		String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
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
