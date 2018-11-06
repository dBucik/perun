package cz.metacentrum.perun.finder.persistence.models.inputEntities.basic;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.BasicInputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.service.IncorrectSourceEntityException;

import java.util.Arrays;
import java.util.List;

import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.EXT_SOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.GROUP_RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER_GROUP;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.VO;

/**
 * Input from user for Group entity.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class GroupInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = GROUP;
	private static final String ENTITY_ID_FIELD = "group_id";
	private static final String ENTITY_TABLE = "groups";
	private static final String ENTITY_ATTRS_TABLE = "group_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(GROUP, VO, EXT_SOURCE, MEMBER, RESOURCE, GROUP_RESOURCE, MEMBER_GROUP);

	public GroupInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
	public String getEntityTable() {
		return ENTITY_TABLE;
	}

	@Override
	public String getEntityIdInAttrValuesTable() {
		return ENTITY_ID_FIELD;
	}

	@Override
	public String getAttrValuesTable() {
		return ENTITY_ATTRS_TABLE;
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
			case VO:
				return getQueryForVo(isSimple);
			case EXT_SOURCE:
				return getQueryForExtSource(isSimple);
			case MEMBER:
				return getQueryForMember(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			case GROUP_RESOURCE:
				return getQueryForGroupResource(isSimple);
			case MEMBER_GROUP:
				return getQueryForMemberGroup(isSimple);
			default:
				throw new IncorrectSourceEntityException("GROUP cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "ent.parent_group_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForVo(boolean isSimple) {
		String select = "ent.vo_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForExtSource(boolean isSimple) {
		String select = "ges.ext_source_id AS foreign_id";
		String join = "JOIN group_ext_sources ges ON ges.group_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "gm.member_id AS foreign_id";
		String join = "JOIN groups_members gm ON gm.group_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "gr.resource_id AS foreign_id";
		String join = "JOIN groups_resources gr ON gr.group_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForGroupResource(boolean isSimple) {
		String select = "gr.group_id AS foreign_id";
		String join = "JOIN groups_resources gr ON gr.group_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMemberGroup(boolean isSimple) {
		String select = "gm.group_id AS foreign_id";
		String join = "JOIN groups_members gm ON gm.group_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}
}
