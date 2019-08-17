package org.squat_team.vis.session;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import lombok.Data;

@Data
public class MenuInfo {

	private Map<Integer, Boolean> selectorMap = new HashMap<>();
	private Map<String, String> searchMap = new HashMap<>();
	
	public boolean getSelectorMenuState(String id) {
		Boolean state = selectorMap.get(Integer.parseInt(id));
		if(state == null) {
			state = false;
		}
		return state;
	}
	
	public void setSelectorMenuState() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param1");
		String state = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param2");
		selectorMap.put(Integer.parseInt(id), Boolean.parseBoolean(state));
	}
	
	public String getSearchState(String id) {
		String state = searchMap.get(id);
		if(state == null) {
			state = "";
		}
		return state;
	}
	
	public void setSearchState() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param1");
		String state = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param2");
		searchMap.put(id, state);
	}
	
}
