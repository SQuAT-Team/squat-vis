package org.squat_team.vis.connector.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * A goal for the optimization. A goal can either be an atomic one or a
 * composite one. Atomic goals are the actually measured ones. To create a
 * composite goal, other composite or atomic goals have to be added as children
 * by calling {@link #addChild()}. All values have to be set,
 * {@link #setRange(CRange)} must not be set for composite goals. <br/>
 * <br/>
 * Note that the ordering of the goals influences
 * {@link CCandidate#setRealValues(List)} and
 * {@link CCandidate#setRealValues(List)}.
 */
@Data
public class CGoal implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -3195903328109371727L;

	private String name;
	private String description;
	private Double expectedResponse;
	private CRange range;
	private List<CGoal> children = new ArrayList<>();
	private CGoal parent;

	public CGoal addChild() {
		CGoal child = new CGoal();
		children.add(child);
		return child;
	}
}
