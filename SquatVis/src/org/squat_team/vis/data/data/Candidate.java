package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Candidate {
	@Id
	@GeneratedValue
	private Long id;
	
	private Long projectId;
	private Long candidateId;
	private Candidate parent;
	private List<Double> realValues = new ArrayList<Double>();
	private List<Double> utilityValues = new ArrayList<Double>();
	private boolean isRealValuePareto = false;
	private boolean isUtilityValuePareto = false;
}
