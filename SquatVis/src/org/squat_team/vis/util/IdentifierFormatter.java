package org.squat_team.vis.util;

/**
 * Assures that identifiers do not contain characters which are not allowed in
 * the frontend.
 */
public class IdentifierFormatter {

	public String format(String identifier) {
		return replaceAllWhitespaces(replaceAllDots(identifier));
	}

	private String replaceAllWhitespaces(String name) {
		return name.replaceAll("\\s+", "-");
	}

	private String replaceAllDots(String name) {
		return name.replaceAll("\\.+", "_");
	}

}
