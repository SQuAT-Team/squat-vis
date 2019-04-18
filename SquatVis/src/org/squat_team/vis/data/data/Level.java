package org.squat_team.vis.data.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Level {
	@Id
	@GeneratedValue
	private Long id;
	
}
