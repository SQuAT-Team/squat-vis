package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.List;

import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Project;

import lombok.Data;

/**
 * Holds all the additional, session-based information about a {@link Project}.
 */
@Data
public class ProjectInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 8781120400605216186L;
	
	public ProjectInfo(List<Candidate> candidates) {
		selectorInfo = new SelectorInfo(candidates);
	}
	
	private SelectorInfo selectorInfo;
	private ColorInfo colorInfo = new ColorInfo();
	private OptionsInfo optionsInfo = new OptionsInfo();
	private TagInfo tagInfo = new TagInfo(optionsInfo);
	private MatrixViewInfo matrixViewInfo = new MatrixViewInfo();
	private StarViewInfo starViewInfo = new StarViewInfo();
	private GraphViewInfo graphViewInfo = new GraphViewInfo();
}
