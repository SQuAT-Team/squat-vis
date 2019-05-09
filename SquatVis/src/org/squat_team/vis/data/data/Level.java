package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * A level of search is a group of {@link Candidate}s which are sent together to
 * the visualization tool. A level therefore describes candidates that are
 * generated with the same optimization configuration and without interruption
 * by user interaction.
 */
@Entity
@Data
public class Level {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Project project;
	@OneToMany
	private List<Candidate> candidates = new ArrayList<>();
	/**
	 * Stores the candidates that are selected for the optimization in the next
	 * level
	 */
	private List<Long> selectedCandidates = new ArrayList<>();
	private boolean candidatesSelected = false;

	/**
	 * Gets the number of all candidates in the level.
	 * 
	 * @return the number of all candidates in this level.
	 */
	public int getNumberOfCandidates() {
		if (candidates == null) {
			return 0;
		} else {
			return candidates.size();
		}
	}
}
