package org.squat_team.vis.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.squat_team.vis.data.data.Candidate;

/**
 * Finds candidates that are non-dominated by all others.
 */
public class ParetoAnalyzer {
	private boolean useUtility;
	private boolean currentCandidateIsDominated = false;
	private List<Candidate> currentParetoCandidates;
	private List<Candidate> noMoreParetoCandidates;

	/**
	 * Creates a new pareto analyzer.
	 * 
	 * @param useUtility
	 */
	public ParetoAnalyzer(boolean useUtility) {
		this.useUtility = useUtility;
	}

	/**
	 * Finds the candidates that are not dominated by other candidates in a group.
	 * 
	 * @param allCandidates the population to test.
	 * @return the non-dominated candidates.
	 */
	public synchronized Collection<Candidate> findParetoCandidates(Collection<Candidate> allCandidates) {
		currentParetoCandidates = new ArrayList<>();
		noMoreParetoCandidates = new ArrayList<>();
		for (Candidate candidate : allCandidates) {
			testIsPareto(candidate);
			handleParetoTestResults(candidate);
		}
		return currentParetoCandidates;
	}

	/**
	 * Tests whether a single candidate is non-dominated.
	 * 
	 * @param candidate the candidate to test.
	 */
	private void testIsPareto(Candidate candidate) {
		currentCandidateIsDominated = false;
		noMoreParetoCandidates.clear();
		for (Candidate currentParetoCandidate : currentParetoCandidates) {
			if(!testDomination(candidate, currentParetoCandidate)) {
				break;
			}
		}
	}

	/**
	 * Tests whether one of the candidates dominates the other.
	 * 
	 * @param candidate the new candidate to test.
	 * @param currentParetoCandidate a candidate from the current set of non-dominated candidates.
	 * @return true if successful, false if result is available.
	 */
	private boolean testDomination(Candidate candidate, Candidate currentParetoCandidate) {
		List<Double> candidateValues = getValues(candidate);
		List<Double> currentParetoCandidateValues = getValues(currentParetoCandidate);

		// compare all values
		boolean candidateDominates = true;
		boolean candidateNonDominated = false;
		boolean allSame = true;
		for (int i = 0; i < candidateValues.size(); i++) {
			if (isBetterThan(candidateValues.get(i), currentParetoCandidateValues.get(i))) {
				candidateNonDominated = true;
				allSame = false;
			} else if (!isEqual(candidateValues.get(i), currentParetoCandidateValues.get(i))) {
				candidateDominates = false;
				allSame = false;
			}
		}

		// handle result
		if (!allSame) {
			if (candidateDominates) {
				noMoreParetoCandidates.add(currentParetoCandidate);
			} else if (!candidateNonDominated) {
				currentCandidateIsDominated = true;
				return false;
			}
		}
		return true;
	}

	/**
	 * The results for a candidate are handled and the class status updated.
	 * 
	 * @param candidate the last analyzed candidate.
	 */
	private void handleParetoTestResults(Candidate candidate) {
		if (!currentCandidateIsDominated) {
			currentParetoCandidates.add(candidate);
			if (!noMoreParetoCandidates.isEmpty()) {
				currentParetoCandidates.removeAll(noMoreParetoCandidates);
			}
		}
	}

	/**
	 * Tests whether the two values are equal.
	 * 
	 * @param candidateValue
	 * @param paretoCandidateValue
	 * @return
	 */
	private boolean isEqual(Double candidateValue, Double paretoCandidateValue) {
		return candidateValue.equals(paretoCandidateValue);
	}
	
	/**
	 * Tests wether the candidate value is better than the current pareto candidate value.
	 * 
	 * @param candidateValue
	 * @param paretoCandidateValue
	 * @return
	 */
	private boolean isBetterThan(Double candidateValue, Double paretoCandidateValue) {
		if (useUtility) {
			return candidateValue > paretoCandidateValue;
		} else {
			return candidateValue < paretoCandidateValue;
		}
	}

	/**
	 * Gets the (goal) values of the candidate.
	 * 
	 * @param candidate
	 * @return
	 */
	private List<Double> getValues(Candidate candidate) {
		if (useUtility) {
			return candidate.getUtilityValues();
		} else {
			return candidate.getRealValues();
		}
	}

}
