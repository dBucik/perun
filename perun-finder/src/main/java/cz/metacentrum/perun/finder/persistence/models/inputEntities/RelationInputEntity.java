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

public abstract class RelationInputEntity extends InputEntity {

	protected RelationInputEntity(PerunEntityType entityType, boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes, List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		super(entityType, isTopLevel, core, attributes, attrNames, innerInputs);
	}

	public abstract String buildSelectFrom(PerunEntityType sourceType, boolean isSimple) throws IncorrectSourceEntityException;

	protected abstract String getPrimaryKey();

	protected abstract String getSecondaryKey();

	protected abstract String getRelationTable();

	@Override
	public Query initQuery() {
		Query query = new Query();
		query.setEntityType(this.getEntityType());
		query.setPrimaryKey(this.getPrimaryKey());
		query.setSecondaryKey(this.getSecondaryKey());

		return query;
	}

	public String getSelectFrom(boolean isSimple, String select, String join) {
		String relationTable = this.getRelationTable();
		String attrNamesTable = this.getAttrNamesTable();
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT ").append(select);
		if (!isSimple) {
			queryString.append(", json_agg(json_build_object(")
					.append("'name', attr_name, 'value', COALESCE(attr_value, attr_value_text), 'type', type)) AS attributes");
		}
		queryString.append(" FROM ").append(relationTable).append(" rel");
		if (!isSimple) {
			queryString.append(" LEFT JOIN ").append(attrNamesTable).append(" an")
					.append(" ON rel.attr_id = an.id");
		}
		if (join != null) {
			queryString.append(' ').append(join);
		}

		return queryString.toString();
	}

	public Query toQuery(PerunEntityType sourceType) throws IncorrectCoreAttributeTypeException, IncorrectSourceEntityException {
		boolean isSimple = PerunEntityType.isSimpleEntity(this.getEntityType()) && this.isSimpleQuery();
		Query query = this.initQuery();

		String selectFrom = this.buildSelectFrom(sourceType, isSimple);
		List<String> names = mergeNames(this.getAttrNames(), this.getAttributes());
		String where = buildWhere(query, this.getCore(), names);

		StringBuilder queryString = new StringBuilder();
		List<Query> innerQueries = new ArrayList<>();

		queryString.append(selectFrom);
		if (! Objects.equals(where, NO_VALUE)) {
			query.setHasWhere(true);
			queryString.append(' ').append(where);
		}

		query.setQueryString(queryString.toString());
		for (InputEntity e : this.getInnerInputs()) {
			innerQueries.add(e.toQuery(this.getEntityType()));
		}
		query.setInnerQueries(innerQueries);
		query.setInputAttributes(this.getAttributes());

		return query;
	}

	private String buildWhere(Query query, List<InputAttribute> core, List<String> attrNames) {
		if ((core == null || core.isEmpty()) && (attrNames == null || attrNames.isEmpty())) {
			return NO_VALUE;
		}

		StringJoiner where = new StringJoiner(" AND ");

		if (core != null) {
			for (InputAttribute attr: core) {
				List<Object> values = attr.getValue();
				StringJoiner subJoiner = new StringJoiner(" OR ");

				for (Object  o: values) {
					String operator = resolveMatchOperator(attr.isLikeMatch(), o);

					switch (operator) {
						case NULL_MATCH:
							subJoiner.add("rel." + attr.getName() + operator);
							break;
						case LIKE_MATCH:
							subJoiner.add("rel." + attr.getName() + "::VARCHAR " + operator + ' ' + query.nextParam('%' + o.toString() + '%'));
							break;
						case EXACT_MATCH:
							subJoiner.add("rel." + attr.getName() + operator + query.nextParam(o));
							break;
					}
				}

				where.add(subJoiner.toString());
			}
		}

		if (attrNames != null) {
			StringJoiner attributes = new StringJoiner(" OR ");
			for (String name: attrNames) {
				attributes.add("(attr_name = " + query.nextParam(name) + ')');
			}

			if (! attrNames.isEmpty()) {
				where.add(attributes.toString());
			}

		}

		return "WHERE " + where.toString();
	}
}
