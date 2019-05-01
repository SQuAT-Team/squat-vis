package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.data.CRange;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.data.Range;

public class RangeImporter extends AbstractImporter<CRange, Range> {

	public RangeImporter(ConnectorService connectorService, Connection connection) {
		super(connectorService, connection);
	}

	@Override
	public Range transform(CRange crange) throws InvalidRequestException {
		if (crange == null) {
			return null;
		}
		Range range = new Range();
		range.setRangeMin(crange.getRangeMin());
		range.setRangeMax(crange.getRangeMax());
		range.setComputeMin(crange.getComputeMin());
		range.setComputeMax(crange.getComputeMax());
		return range;
	}
}
