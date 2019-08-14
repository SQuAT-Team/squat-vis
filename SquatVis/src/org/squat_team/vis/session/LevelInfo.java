package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import lombok.Data;

@Data
public class LevelInfo implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 87339120559980298L;

	private LevelMode mode = LevelMode.LAST_AND_PARENT;
	private Set<Integer> level = new HashSet<>();
	private Set<Integer> parent = new HashSet<>();

	public void setLevelMode() {
		String newMode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		switch(newMode) {
		case "LAST":
			this.mode = LevelMode.LAST;
			break;
		case "LAST_AND_PARENT":
			this.mode = LevelMode.LAST_AND_PARENT;
			break;
		case "ALL":
			this.mode = LevelMode.ALL;
			break;
		case "CUSTOM":
			this.mode = LevelMode.CUSTOM;
			break;
		}
	}
	
	public void addActiveLevel() {
		String numberOfLevel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		level.add(Integer.parseInt(numberOfLevel));
	}
	
	public void removeActiveLevel() {
		String numberOfLevel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		level.remove(Integer.parseInt(numberOfLevel));
	}
	
	public void addActiveParentLevel() {
		String numberOfLevel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		parent.add(Integer.parseInt(numberOfLevel));
	}
	
	public void removeActiveParentLevel() {
		String numberOfLevel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		parent.remove(Integer.parseInt(numberOfLevel));
	}
	
	public Set<Integer> getActiveLevels(int numberOfLevels) {
		switch (mode) {
		case LAST:
		case LAST_AND_PARENT:
			Set<Integer> lastLevel = new HashSet<>();
			if(numberOfLevels >= 1) {
				lastLevel.add(numberOfLevels - 1);
			}
			return lastLevel;
		case CUSTOM:
			return level;
		case ALL:
			Set<Integer> allLevels = new HashSet<>();
			for(int i = 0; i < numberOfLevels; i++) {
				allLevels.add(i);
			}
			return allLevels;
		default:
			return new HashSet<>();
		}
	}
	
	public boolean isActiveLevel(int numberOfLevel) {
		return level.contains(numberOfLevel);
	}
	
	public boolean isActiveParentLevel(int numberOfLevel) {
		return parent.contains(numberOfLevel);
	}

	public Set<Integer> getActiveParentLevels(int numberOfLevels) {
		switch (mode) {
		case LAST_AND_PARENT:
			Set<Integer> previousOfLastLevel = new HashSet<>();
			if(numberOfLevels >= 2) {
				previousOfLastLevel.add(numberOfLevels - 2);
			}
			return previousOfLastLevel;
		case CUSTOM:
			return parent;
		case LAST:
		case ALL:
		default:
			return new HashSet<>();
		}
	}

	public enum LevelMode {
		LAST, LAST_AND_PARENT, ALL, CUSTOM;
	}
}
