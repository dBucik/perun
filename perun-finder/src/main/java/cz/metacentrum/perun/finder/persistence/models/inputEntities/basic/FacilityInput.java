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
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.HOST;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER_FACILITY;

public class FacilityInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = FACILITY;
	private static final String ENTITY_ID_FIELD = "facility_id";
	private static final String ENTITY_TABLE = "facilities";
	private static final String ENTITY_ATTRS_TABLE = "facility_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(USER, RESOURCE, HOST, USER_FACILITY);

	public FacilityInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
			case USER:
				return getQueryForUser(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			case HOST:
				return getQueryForHost(isSimple);
			case USER_FACILITY:
				return getQueryForUserFacility(isSimple);
			default:
				throw new IncorrectSourceEntityException("FACILITY cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForUser(boolean isSimple) {
		String select = "ufav.user_id AS foreign_id";
		String join = "JOIN user_facility_attr_values ufav ON ufav.facility_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "r.id AS foreign_id";
		String join = "JOIN resources r ON r.facility_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForHost(boolean isSimple) {
		String select = "h.id AS foreign_id";
		String join = "JOIN hosts h ON h.facility_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForUserFacility(boolean isSimple) {
		String select = "ufav.facility_id AS foreign_id";
		String join = "JOIN user_facility_attr_values ufav ON ufav.facility_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}
}
