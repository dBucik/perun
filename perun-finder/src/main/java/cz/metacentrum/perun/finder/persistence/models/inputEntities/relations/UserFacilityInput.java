package cz.metacentrum.perun.finder.persistence.models.inputEntities.relations;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.RelationInputEntity;
import cz.metacentrum.perun.finder.service.IncorrectSourceEntityException;

import java.util.Arrays;
import java.util.List;

import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER_FACILITY;

public class UserFacilityInput extends RelationInputEntity {

	private static final PerunEntityType TYPE = USER_FACILITY;
	private static final String RELATION_TABLE = "user_facility_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";
	private static final String PRIMARY_KEY = "user_id";
	private static final String SECONDARY_KEY = "facility_id";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(USER, FACILITY);

	public UserFacilityInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
			case USER:
				return getQueryForUser(isSimple);
			case FACILITY:
				return getQueryForFacility(isSimple);
			default:
				throw new IncorrectSourceEntityException("USER_FACILITY cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		String select = "rel.user_id, rel.facility_id";

		return this.getSelectFrom(isSimple, select, null);
	}

	private String getQueryForUser(boolean isSimple) {
		String select = "rel.user_id, rel.facility_id, rel.user_id AS foreign_id";
		String join = "JOIN users u ON u.id = rel.user_id";

		return this.getSelectFrom(isSimple, select, join);
	}

	private String getQueryForFacility(boolean isSimple) {
		String select = "rel.user_id, rel.facility_id, rel.facility_id AS foreign_id";
		String join = "JOIN facilities f ON f.id = rel.facility_id";

		return this.getSelectFrom(isSimple, select, join);
	}
}
