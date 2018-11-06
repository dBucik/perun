package cz.metacentrum.perun.finder.persistence.models.inputEntities.basic;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.BasicInputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.service.IncorrectSourceEntityException;

import java.util.Arrays;
import java.util.List;

import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER_GROUP;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER_RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.VO;

/**
 * Input from user for Member entity.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class MemberInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = MEMBER;
	private static final String ENTITY_ID_FIELD = "member_id";
	private static final String ENTITY_TABLE = "members";
	private static final String ENTITY_ATTRS_TABLE = "member_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(VO, USER, RESOURCE, GROUP, MEMBER_GROUP, MEMBER_RESOURCE);

	public MemberInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
			case VO:
				return getQueryForVo(isSimple);
			case USER:
				return getQueryForUser(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			case GROUP:
				return getQueryForGroup(isSimple);
			case MEMBER_GROUP:
				return getQueryForMemberGroup(isSimple);
			case MEMBER_RESOURCE:
				return getQueryForMemberResource(isSimple);
			default:
				throw new IncorrectSourceEntityException("MEMBER cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForVo(boolean isSimple) {
		String select = "ent.vo_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForUser(boolean isSimple) {
		String select = "ent.user_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "mrav.resource_id AS foreign_id";
		String join = "JOIN member_resource_attr_values mrav ON mrav.member_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "gm.group_id AS foreign_id";
		String join = "JOIN groups_members gm ON gm.member_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMemberGroup(boolean isSimple) {
		String select = "gm.member_id AS foreign_id";
		String join = "JOIN groups_members gm ON gm.member_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMemberResource(boolean isSimple) {
		String select = "mrav.member_id AS foreign_id";
		String join = "JOIN member_resource_attr_values mrav ON mrav.member_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}
}
