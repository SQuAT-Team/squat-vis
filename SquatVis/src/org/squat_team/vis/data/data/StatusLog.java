package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * A class that stores status updates ({@link StatusLogEntry}). Each log is
 * related to exactly one {@link Project}.
 */
@Entity
@Data
public class StatusLog {
	@Id
	@GeneratedValue
	private long id;

	private List<StatusLogEntry> entries = new ArrayList<>();

}
