package org.squat_team.vis.test.analysis;

import java.util.Date;

import org.squat_team.vis.test.analysis.Status.StatusType;

import lombok.Data;
import lombok.NonNull;

/**
 * The entry in a {@link StatusLog}. Each entry has to be initialized by calling
 * {@link #initialize(Date, StatusType, String)}.
 */
@Data
public class StatusLogEntry {
	private Date date;
	private StatusType type;
	private String description;

	private DateFormatter dateFormatter = new DateFormatter();

	/**
	 * Initializes the entry with all necessary data.
	 * 
	 * @param date        the date at which the update was made
	 * @param type        the resulting type of the update
	 * @param description the description provided with the update
	 * @return
	 */
	public StatusLogEntry initialize(@NonNull Date date, @NonNull StatusType type, @NonNull String description) {
		this.date = date;
		this.type = type;
		this.description = description;
		return this;
	}

	/**
	 * Gets the formated date of the update.
	 * 
	 * @return the date in human-readable form.
	 */
	public String getDate() {
		return dateFormatter.format(date);
	}

}
