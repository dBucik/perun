package cz.metacentrum.perun.finder.persistence.data;

import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.Query;

import java.util.List;

/**
 * GeneralSearcher access to the DB
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public interface GeneralSearcherDAO {

	/**
	 * Execute query constructed from the passed parameter.
	 * @param query object containing all information for generating SQL query
	 * @return List of entities that meet all the criteria
	 */
	List<PerunEntity> executeQuery(Query query);

	/**
	 * Execute query constructed from the passed parameter.
	 * @param query object containing all information for generating SQL query
	 * @return List of id for entities that meet all the criteria
	 */
	List<Integer> executeQueryForIds(Query query);

}
