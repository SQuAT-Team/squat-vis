package org.squat_team.vis.data.data;

import javax.persistence.Embeddable;

import lombok.Data;

// TODO: Keep?
@Embeddable
@Data
public class ToolConnection {
	private String toolName;
	private String ip;
}
