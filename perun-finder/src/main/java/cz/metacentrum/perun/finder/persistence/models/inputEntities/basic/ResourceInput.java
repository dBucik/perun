package cz.metacentrum.perun.finder.persistence.models.inputEntities.basic;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.BasicInputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.service.IncorrectSourceEntityException;

import java.util.Arrays;
import java.util.List;

import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.GROUP_RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER_RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.SERVICE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.VO;

public class ResourceInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = RESOURCE;
	private static final String ENTITY_ID_FIELD = "resource_id";
	private static final String ENTITY_TABLE = "resources";
	private static final String ENTITY_ATTRS_TABLE = "resource_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(MEMBER, VO, SERVICE, FACILITY, GROUP, MEMBER_RESOURCE, GROUP_RESOURCE);

	public ResourceInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
			case MEMBER:
				return getQueryForMember(isSimple);
			case VO:
				return getQueryForVo(isSimple);
			case SERVICE:
				return getQueryForService(isSimple);
			case FACILITY:
				return getQueryForFacility(isSimple);
			case GROUP:
				return getQueryForGroup(isSimple);
			case MEMBER_RESOURCE:
				return getQueryForMemberResource(isSimple);
			case GROUP_RESOURCE:
				return getQueryForGroupResource(isSimple);
			default:
				throw new IncorrectSourceEntityException("RESOURCE cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "mrav.member_id AS foreign_id";
		String join = "JOIN member_resource_attr_values mrav ON mrav.resource_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForVo(boolean isSimple) {
		String select = "ent.vo_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForService(boolean isSimple) {
		String select = "rs.service_id AS foreign_id";
		String join = "JOIN resource_services rs ON rs.resource_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForFacility(boolean isSimple) {
		String select = "ent.facility_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "gr.group_id AS foreign_id";
		String join = "JOIN groups_resources gr ON gr.resource_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMemberResource(boolean isSimple) {
		String select = "mrav.resource_id AS foreign_id";
		String join = "JOIN member_resource_attr_values mrav ON mrav.resource_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForGroupResource(boolean isSimple) {
		String select = "gr.resource_id AS foreign_id";
		String join = "JOIN groups_resources gr ON gr.resource_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}
}
