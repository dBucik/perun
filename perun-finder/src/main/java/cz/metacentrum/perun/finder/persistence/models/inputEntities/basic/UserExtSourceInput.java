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
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.USER_EXT_SOURCE;

/**
 * Input from user for UserExtSource entity.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class UserExtSourceInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = USER_EXT_SOURCE;
	private static final String ENTITY_ID_FIELD = "user_ext_source_id";
	private static final String ENTITY_TABLE = "user_ext_sources";
	private static final String ENTITY_ATTRS_TABLE = "user_ext_source_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(USER, EXT_SOURCE);

	public UserExtSourceInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
			case EXT_SOURCE:
				return getQueryForExtSource(isSimple);
			default:
				throw new IncorrectSourceEntityException("USER_EXT_SOURCE cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForUser(boolean isSimple) {
		String select = "ent.user_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForExtSource(boolean isSimple) {
		String select = "ent.ext_sources_id AS foreign_id";

		return this.getSelectFrom(isSimple, select, null, ENTITY_TABLE);
	}
}
