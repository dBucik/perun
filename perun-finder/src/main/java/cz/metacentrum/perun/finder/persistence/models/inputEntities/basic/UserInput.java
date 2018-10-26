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
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER_EXT_SOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER_FACILITY;

public class UserInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = USER;
	private static final String ENTITY_ID_FIELD = "user_id";
	private static final String ENTITY_TABLE = "users";
	private static final String ENTITY_ATTRS_TABLE = "user_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(FACILITY, MEMBER, USER_EXT_SOURCE, USER_FACILITY);

	public UserInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
			case FACILITY:
				return getQueryForFacility(isSimple);
			case USER_EXT_SOURCE:
				return getQueryForUserExtSource(isSimple);
			case MEMBER:
				return getQueryForMember(isSimple);
			case USER_FACILITY:
				return getQueryForUserFacility(isSimple);
			default:
				throw new IncorrectSourceEntityException("USER cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForFacility(boolean isSimple) {
		String select = "ufav.facility_id AS foreign_id";
		String join = "JOIN user_facility_attr_values ufav ON ufav.user_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForUserExtSource(boolean isSimple) {
		String select = "ues.id AS foreign_id";
		String join = "JOIN user_ext_sources ues ON ues.user_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "m.id AS foreign_id";
		String join = "JOIN members m ON m.user_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForUserFacility(boolean isSimple) {
		String select = "ufav.user_id AS foreign_id";
		String join = "JOIN user_facility_attr_values ufav ON ufav.user_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}
}
