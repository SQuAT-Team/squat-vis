package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.exceptions.InvalidRequestException;

public interface IImporter<CData, Data> {
	public Data transform(CData connectorData) throws InvalidRequestException;
}
