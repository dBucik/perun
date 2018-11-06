package cz.metacentrum.perun.finder.persistence.models.inputEntities.relations;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.RelationInputEntity;
import cz.metacentrum.perun.finder.service.IncorrectSourceEntityException;

import java.util.Arrays;
import java.util.List;

import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.GROUP_RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.RESOURCE;

/**
 * Input from user for Group Resource relation.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class GroupResourceInput extends RelationInputEntity {

	private static final PerunEntityType TYPE = GROUP_RESOURCE;
	private static final String RELATION_TABLE = "group_resource_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";
	private static final String PRIMARY_KEY = "group_id";
	private static final String SECONDARY_KEY = "resource_id";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(GROUP, RESOURCE);

	public GroupResourceInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
							  List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		super(TYPE, isTopLevel, core, attributes, attrNames, innerInputs);
	}

	@Override
	public boolean isAllowedInnerInput(PerunEntityType entityType) {
		if (entityType == null) {
			return false;
		}

		return ALLOWED_INNER_INPUTS.contains(entityType);
	}

	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}

	@Override
	public String getSecondaryKey() {
		return SECONDARY_KEY;
	}

	@Override
	protected String getRelationTable() {
		return RELATION_TABLE;
	}

	@Override
	public String getAttrNamesTable() {
		return ATTR_NAMES_TABLE;
	}

	@Override
	public String buildSelectFrom(PerunEntityType sourceType, boolean isSimple) throws IncorrectSourceEntityException {
		if (sourceType == null) {
			return getDefaultQuery(isSimple);
		}

		switch (sourceType) {
			case GROUP:
				return getQueryForGroup(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			default:
				throw new IncorrectSourceEntityException("GROUP_RESOURCE cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		String select = "rel.group_id, rel.resource_id";

		return this.getSelectFrom(isSimple, select, null);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "rel.group_id, rel.resource_id, rel.group_id AS foreign_id";
		String join = "JOIN groups g ON g.id = rel.group_id";

		return this.getSelectFrom(isSimple, select, join);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "rel.group_id, rel.resource_id, rel.resource_id AS foreign_id";
		String join = "JOIN resources r ON r.id = rel.resource_id";

		return this.getSelectFrom(isSimple, select, join);
	}
}
