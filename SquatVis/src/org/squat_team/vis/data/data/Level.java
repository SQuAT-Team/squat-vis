package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.List;

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

	private List<Candidate> candidates = new ArrayList<Candidate>();
}
