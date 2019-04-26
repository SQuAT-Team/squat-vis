package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Goal {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private String color;
	private Double expectedResponse;
	private Range range;
	private int index;
	
	@OneToMany
	private List<Goal> children = new ArrayList<Goal>();
	@ManyToOne
	private Goal parent;
}
