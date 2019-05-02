package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.exceptions.InvalidRequestException;

/**
 * Importers are used to import data type from the Connector interface into data
 * types supported by the database and store the data there.
 * 
 * @param <C> Connector data type that is imported
 * @param <D> Corresponding database value that is returned
 */
public interface IImporter<C, D> {

	/**
	 * Transforms the connector data type into a data type that can be stored in the
	 * database and persists it.
	 * 
	 * @param connectorData data provided by the Connector interface
	 * @return the persisted data
	 * @throws InvalidRequestException If the provided data violates constraints
	 */
	public D transform(C connectorData) throws InvalidRequestException;
}
