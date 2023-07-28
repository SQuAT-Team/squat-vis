package org.squat_team.vis.test.analysis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * A class that stores status updates ({@link StatusLogEntry}). Each log is
 * related to exactly one {@link Project}.
 */
@Data
public class StatusLog {
	private long id;

	private List<StatusLogEntry> entries = new ArrayList<>();

}
