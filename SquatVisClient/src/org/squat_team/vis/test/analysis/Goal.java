package org.squat_team.vis.test.analysis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Goals are directly linked to a {@link Project}. A goal can contain several
 * subgoals. Thus, there are composite and atomic goals. Composite goals group
 * other composite goals and atomic goals, while atomic goals are the actually
 * 'measured' ones.
 */
@Data
public class Goal {
	private Long id;
	private String name;
	private String description;
	private Double expectedResponse;
	private Range range;
	private int index = -1;

	private List<Goal> children = new ArrayList<>();
	private Goal parent;

	public int getNumberOfChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}
}
