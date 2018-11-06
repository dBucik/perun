package cz.metacentrum.perun.finder.persistence.models.inputEntities;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.Query;
import cz.metacentrum.perun.finder.service.IncorrectSourceEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Basic model for input from user specifying entity.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public abstract class BasicInputEntity extends InputEntity {

	protected BasicInputEntity(PerunEntityType entityType, boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes, List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		super(entityType, isTopLevel, core, attributes, attrNames, innerInputs);
	}

	/**
	 * Get DB table name of entity
	 * @return name of table
	 */
	public abstract String getEntityTable();

	/**
	 * Get entity_id field name in attrValuesTable
	 * @return field name
	 */
	protected abstract String getEntityIdInAttrValuesTable();

	/**
	 * Get DB table name of entity attrs
	 * @return name of table
	 */
	protected abstract String getAttrValuesTable();

	@Override
	public Query initQuery() {
		Query query = new Query();
		query.setEntityType(this.getEntityType());

		return query;
	}

	@Override
	public Query toQuery(PerunEntityType sourceType) throws IncorrectCoreAttributeTypeException, IncorrectSourceEntityException {
		boolean isSimple = PerunEntityType.isSimpleEntity(this.getEntityType()) && this.isSimpleQuery();
		Query query = this.initQuery();

		String selectFrom = this.buildSelectFrom(sourceType, isSimple);
		String where = outerWhere(query, this.getCore());

		StringBuilder queryString = new StringBuilder();
		List<Query> innerQueries = new ArrayList<>();

		queryString.append(selectFrom);
		if (! isSimple) {
			List<String> names = mergeNames(this.getAttrNames(), this.getAttributes());
			String attributesQuery = buildAttributesQuery(query, names);
			queryString.append(" LEFT JOIN (").append(attributesQuery)
					.append(") AS attributes ON ent.id = attributes.entity_id");
		}

		if (! Objects.equals(where, NO_VALUE)) {
			queryString.append(' ').append(where);
			query.setHasWhere(true);
		}

		query.setQueryString(queryString.toString());
		for (InputEntity e : this.getInnerInputs()) {
			innerQueries.add(e.toQuery(this.getEntityType()));
		}
		query.setInnerQueries(innerQueries);
		query.setInputAttributes(this.getAttributes());

		return query;
	}

	/**
	 * Get SELECT and FROM parts of the query
	 * @param isSimple TRUE if the query does not have to query attributes
	 * @param select SELECT part
	 * @param join JOIN part
	 * @param entityTable name of DB table for entity
	 * @return String with SELECT and FROM parts
	 */
	public String getSelectFrom(boolean isSimple, String select, String join, String entityTable) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT to_json(ent) AS entity");
		if (select != null) {
			queryString.append(", ").append(select);
		}

		if (! isSimple) {
			queryString.append(", attributes.data AS attributes");
		}
		queryString.append(" FROM ").append(entityTable).append(" ent");
		if (join != null) {
			queryString.append(' ').append(join);
		}

		return queryString.toString();
	}

	/**
	 * Build query for attributes
	 * @param query query object
	 * @param attrNames names of attributes to be fetched
	 * @return String containing query
	 */
	protected String buildAttributesQuery(Query query, List<String> attrNames) {
		String entityId = this.getEntityIdInAttrValuesTable();
		String attrValuesTable = this.getAttrValuesTable();
		String attrNamesTable = this.getAttrNamesTable();

		StringBuilder queryString = new StringBuilder();
		String where = buildAttributesWhere(query, attrNames);

		queryString.append("SELECT ").append(entityId).append(" AS entity_id, json_agg(json_build_object(")
				.append("'name', attr_name, 'value', COALESCE(attr_value, attr_value_text), 'type', type)) AS data")
				.append(" FROM ").append(attrValuesTable).append(" av JOIN ").append(attrNamesTable).append(" an")
				.append(" ON av.attr_id = an.id ");
		if (!Objects.equals(where, NO_VALUE)) {
			queryString.append(where).append(' ');
		}
		queryString.append("GROUP BY ").append(entityId);

		return queryString.toString();
	}

	/**
	 * Build WHERE part for the attributes query
	 * @param query query object
	 * @param attrNames names of attributes to be fetched
	 * @return String containing WHERE part
	 */
	protected String buildAttributesWhere(Query query, List<String> attrNames) {
		if (attrNames == null || attrNames.isEmpty()) {
			return NO_VALUE;
		}

		StringJoiner where = new StringJoiner(" OR ");
		for (String name: attrNames) {
			where.add("(attr_name = " + query.nextParam(name) + ')');
		}

		return "WHERE " + where.toString();
	}

	/**
	 * Build WHERE part for entity
	 * @param query query object
	 * @param core list of core attributes
	 * @return String containing WHERE part
	 */
	private String outerWhere(Query query, List<InputAttribute> core) {
		if (core == null || core.isEmpty()) {
			return NO_VALUE;
		}

		StringJoiner where = new StringJoiner(" AND ");

		for (InputAttribute attr: core) {
			List<Object> values = attr.getValue();
			StringJoiner subJoiner = new StringJoiner(" OR ");

			for (Object  o: values) {
				String operator = resolveMatchOperator(attr.isLikeMatch(), o);

				switch (operator) {
					case NULL_MATCH:
						subJoiner.add("ent." + attr.getName() + operator);
						break;
					case LIKE_MATCH:
						subJoiner.add("ent." + attr.getName() + "::VARCHAR " + operator + ' ' + query.nextParam('%' + o.toString() + '%'));
						break;
					case EXACT_MATCH:
						subJoiner.add("ent." + attr.getName() + operator + query.nextParam(o));
						break;
				}
			}

			where.add(subJoiner.toString());
		}

		return "WHERE " + where.toString();
	}
}
