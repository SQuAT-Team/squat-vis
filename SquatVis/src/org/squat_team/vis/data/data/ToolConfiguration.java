package org.squat_team.vis.data.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ToolConfiguration {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String toolName;
	private Boolean hasRealValues;
	private Boolean hasUtilities;
	private Boolean hasArchitectures;
	
	// TODO: add more
}
