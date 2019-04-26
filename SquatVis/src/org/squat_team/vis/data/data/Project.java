package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Project {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Date lastModified;
	private Integer numberOfGoals;
	@OneToOne
	private ToolConfiguration configuration;
	private Status status;
	@OneToOne
	private Goal goal;
	@OneToMany
	private List<Level> levels = new ArrayList<Level>();
}
