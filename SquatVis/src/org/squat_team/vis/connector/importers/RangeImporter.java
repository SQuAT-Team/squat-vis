package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CRange;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.data.Range;

/**
 * Imports {@link CRange}s and returns {@link Range} objects, which are then
 * stored in the database.
 */
public class RangeImporter extends AbstractImporter<CRange, Range> {

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public RangeImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public Range transform(CRange crange) throws InvalidRequestException {
		if (crange == null) {
			return null;
		}
		return transformRange(crange);
	}

	/**
	 * Applies the transformation on the object level.
	 * 
	 * @param crange the range to transform
	 * @return the transformed range
	 */
	private Range transformRange(CRange crange) {
		Range range = new Range();
		range.setRangeMin(crange.getRangeMin());
		range.setRangeMax(crange.getRangeMax());
		range.setComputeMin(crange.getComputeMin());
		range.setComputeMax(crange.getComputeMax());
		return range;
	}
}
