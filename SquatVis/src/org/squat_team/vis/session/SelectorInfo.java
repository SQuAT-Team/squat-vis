package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import lombok.Data;

@Data
@Named
@SessionScoped
public class SelectorInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -6415382161054942298L;

	private Set<String> selected = new HashSet<>();

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
		;
	}

}
