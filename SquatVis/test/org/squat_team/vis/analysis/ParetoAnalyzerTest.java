package org.squat_team.vis.analysis;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.squat_team.vis.data.data.Candidate;

/**
 * Tests the {@link ParetoAnalyzer}.
 */
public class ParetoAnalyzerTest {

	/**
	 * Tests two candidates with one same values.
	 */
	@Test
	public void testOneValueSame() {
		Candidate c1 = new Candidate();
		Double[] c1Values = { 1d, 100d, 100d };
		c1.setUtilityValues(Arrays.asList(c1Values));

		Candidate c2 = new Candidate();
		Double[] c2Values = { 1d, 1d, 1d };
		c2.setUtilityValues(Arrays.asList(c2Values));

		Candidate[] allCandidates = { c1, c2 };
		ParetoAnalyzer analyzer = new ParetoAnalyzer(true);
		Collection<Candidate> paretoCandidates = analyzer.findParetoCandidates(Arrays.asList(allCandidates));

		assertEquals(1, paretoCandidates.size());
		assertTrue(paretoCandidates.contains(c1));
	}

	/**
	 * Tests two candidates with identical values.
	 */
	@Test
	public void testSameValues() {
		Candidate c1 = new Candidate();
		Double[] c1Values = { 1d, 100d, 100d };
		c1.setUtilityValues(Arrays.asList(c1Values));

		Candidate c2 = new Candidate();
		Double[] c2Values = { 1d, 100d, 100d };
		c2.setUtilityValues(Arrays.asList(c2Values));

		Candidate[] allCandidates = { c1, c2 };
		ParetoAnalyzer analyzer = new ParetoAnalyzer(true);
		Collection<Candidate> paretoCandidates = analyzer.findParetoCandidates(Arrays.asList(allCandidates));

		assertEquals(2, paretoCandidates.size());
		assertTrue(paretoCandidates.contains(c1));
		assertTrue(paretoCandidates.contains(c2));
	}

	/**
	 * Tests two candidates and the second one dominates the first.
	 */
	@Test
	public void testNewDominates() {
		Candidate c1 = new Candidate();
		Double[] c1Values = { 1d, 100d, 100d };
		c1.setUtilityValues(Arrays.asList(c1Values));

		Candidate c2 = new Candidate();
		Double[] c2Values = { 1d, 101d, 101d };
		c2.setUtilityValues(Arrays.asList(c2Values));

		Candidate[] allCandidates = { c1, c2 };
		ParetoAnalyzer analyzer = new ParetoAnalyzer(true);
		Collection<Candidate> paretoCandidates = analyzer.findParetoCandidates(Arrays.asList(allCandidates));

		assertEquals(1, paretoCandidates.size());
		assertTrue(paretoCandidates.contains(c2));
	}

	/**
	 * Tests two candidates and the first one dominates the second.
	 */
	@Test
	public void testOldDominates() {
		Candidate c1 = new Candidate();
		Double[] c1Values = { 1d, 100d, 100d };
		c1.setUtilityValues(Arrays.asList(c1Values));

		Candidate c2 = new Candidate();
		Double[] c2Values = { 1d, 99d, 99d };
		c2.setUtilityValues(Arrays.asList(c2Values));

		Candidate[] allCandidates = { c1, c2 };
		ParetoAnalyzer analyzer = new ParetoAnalyzer(true);
		Collection<Candidate> paretoCandidates = analyzer.findParetoCandidates(Arrays.asList(allCandidates));

		assertEquals(1, paretoCandidates.size());
		assertTrue(paretoCandidates.contains(c1));
	}

	/**
	 * Tests four candidates and the last one dominates all.
	 */
	@Test
	public void testNewDominatesAll() {
		Candidate c1 = new Candidate();
		Double[] c1Values = { 1d, 1d, 100d };
		c1.setUtilityValues(Arrays.asList(c1Values));

		Candidate c2 = new Candidate();
		Double[] c2Values = { 100d, 100d, 1d };
		c2.setUtilityValues(Arrays.asList(c2Values));

		Candidate c3 = new Candidate();
		Double[] c3Values = { 1d, 100d, 1d };
		c3.setUtilityValues(Arrays.asList(c3Values));

		Candidate c4 = new Candidate();
		Double[] c4Values = { 100d, 100d, 100d };
		c4.setUtilityValues(Arrays.asList(c4Values));

		Candidate[] allCandidates = { c1, c2, c3, c4 };
		ParetoAnalyzer analyzer = new ParetoAnalyzer(true);
		Collection<Candidate> paretoCandidates = analyzer.findParetoCandidates(Arrays.asList(allCandidates));

		assertEquals(1, paretoCandidates.size());
		assertTrue(paretoCandidates.contains(c4));
	}

	/**
	 * Tests four candidates and the last is non-dominated by all others.
	 */
	@Test
	public void testNewCandidateNotDominated() {
		Candidate c1 = new Candidate();
		Double[] c1Values = { 1d, 1d, 100d };
		c1.setUtilityValues(Arrays.asList(c1Values));

		Candidate c2 = new Candidate();
		Double[] c2Values = { 1d, 100d, 1d };
		c2.setUtilityValues(Arrays.asList(c2Values));

		Candidate c3 = new Candidate();
		Double[] c3Values = { 100d, 1d, 1d };
		c3.setUtilityValues(Arrays.asList(c3Values));

		Candidate c4 = new Candidate();
		Double[] c4Values = { 99d, 99d, 99d };
		c4.setUtilityValues(Arrays.asList(c4Values));

		Candidate[] allCandidates = { c1, c2, c3, c4 };
		ParetoAnalyzer analyzer = new ParetoAnalyzer(true);
		Collection<Candidate> paretoCandidates = analyzer.findParetoCandidates(Arrays.asList(allCandidates));

		assertEquals(4, paretoCandidates.size());
		assertTrue(paretoCandidates.contains(c1));
		assertTrue(paretoCandidates.contains(c2));
		assertTrue(paretoCandidates.contains(c3));
		assertTrue(paretoCandidates.contains(c4));
	}

	/**
	 * Tests four candidates and the last dominates two others.
	 */
	@Test
	public void testNewCandidateDominatesSome() {
		Candidate c1 = new Candidate();
		Double[] c1Values = { 1d, 1d, 100d };
		c1.setUtilityValues(Arrays.asList(c1Values));

		Candidate c2 = new Candidate();
		Double[] c2Values = { 1d, 100d, 1d };
		c2.setUtilityValues(Arrays.asList(c2Values));

		Candidate c3 = new Candidate();
		Double[] c3Values = { 100d, 1d, 1d };
		c3.setUtilityValues(Arrays.asList(c3Values));

		Candidate c4 = new Candidate();
		Double[] c4Values = { 100d, 100d, 2d };
		c4.setUtilityValues(Arrays.asList(c4Values));

		Candidate[] allCandidates = { c1, c2, c3, c4 };
		ParetoAnalyzer analyzer = new ParetoAnalyzer(true);
		Collection<Candidate> paretoCandidates = analyzer.findParetoCandidates(Arrays.asList(allCandidates));

		assertEquals(2, paretoCandidates.size());
		assertTrue(paretoCandidates.contains(c1));
		assertTrue(paretoCandidates.contains(c4));
	}

}
