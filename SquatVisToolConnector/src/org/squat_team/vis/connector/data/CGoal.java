package org.squat_team.vis.connector.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CGoal implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -3195903328109371727L;

	private String name;
	private String description;
	private Double expectedResponse;
	private CRange range;
	private List<CGoal> children = new ArrayList<CGoal>();
	private CGoal parent;

	public CGoal addChild() {
		CGoal child = new CGoal();
		children.add(child);
		return child;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getExpectedResponse() {
		return expectedResponse;
	}

	public void setExpectedResponse(Double expectedResponse) {
		this.expectedResponse = expectedResponse;
	}

	public CRange getRange() {
		return range;
	}

	public void setRange(CRange range) {
		this.range = range;
	}

	public List<CGoal> getChildren() {
		return children;
	}

	public void setChildren(List<CGoal> children) {
		this.children = children;
	}

	public CGoal getParent() {
		return parent;
	}

	public void setParent(CGoal parent) {
		this.parent = parent;
	}
}
