package cz.metacentrum.perun.finder.persistence.models;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Query object containing all information for generating SQL query
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class Query {

	private String primaryKey;
	private String secondaryKey;

	private PerunEntityType entityType;
	private String queryString;
	private MapSqlParameterSource parameters = new MapSqlParameterSource();

	private int paramCounter = 0;
	private List<Query> innerQueries = new ArrayList<>();
	private List<InputAttribute> inputAttributes;

	private boolean hasWhere;

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getSecondaryKey() {
		return secondaryKey;
	}

	public void setSecondaryKey(String secondaryKey) {
		this.secondaryKey = secondaryKey;
	}

	public PerunEntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(PerunEntityType entityType) {
		this.entityType = entityType;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public List<Query> getInnerQueries() {
		return innerQueries;
	}

	public void setInnerQueries(List<Query> innerQueries) {
		this.innerQueries = innerQueries;
	}

	public List<InputAttribute> getInputAttributes() {
		return inputAttributes;
	}

	public void setInputAttributes(List<InputAttribute> inputAttributes) {
		this.inputAttributes = inputAttributes;
	}

	public void setHasWhere(boolean hasWhere) {
		this.hasWhere = hasWhere;
	}

	public MapSqlParameterSource getParameters() {
		return parameters;
	}

	/**
	 * Add parameter
	 * @param value value
	 * @return key of the parameter in query
	 */
	public String nextParam(Object value) {
		paramCounter++;
		parameters.addValue(String.valueOf(paramCounter), value);
		return ':' + String.valueOf(paramCounter);
	}

	/**
	 * Set ids retrieved from inner queries for Entity.
	 * @param ids IDs
	 */
	public void setIds(Set<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}

		String query = "ent.id IN (" + nextParam(new ArrayList<>(ids)) + ')';
		if (! this.hasWhere) {
			query = " WHERE " + query;
		}

		this.queryString += query;
	}

	/**
	 * Set ids retrieved from inner queries for Relation.
	 * @param key1 key for first entity in relation
	 * @param ids1 ids for first entity
	 * @param key2 key for second entity in relation
	 * @param ids2 ids for second entity
	 */
	public void setIds(String key1, Set<Integer> ids1, String key2, Set<Integer> ids2) {
		StringJoiner query = new StringJoiner(" AND ");
		if ((ids1 != null && !ids1.isEmpty()) || (ids2 != null && !ids2.isEmpty())) {
			if (key1 != null && ids1 != null && !ids1.isEmpty()) {
				query.add("rel." + key1 + " IN (" + nextParam(new ArrayList<>(ids1)) + ')');
			}

			if (key2 != null && ids2 != null && !ids2.isEmpty()) {
				query.add("rel." + key2 + " IN (" + nextParam(new ArrayList<>(ids2)) + ')');
			}

			if (!this.hasWhere) {
				this.queryString += " WHERE ";
			}

			this.queryString += query.toString();
		}
		this.queryString += " GROUP BY rel." + getPrimaryKey() + ", rel." + getSecondaryKey();
	}
}
