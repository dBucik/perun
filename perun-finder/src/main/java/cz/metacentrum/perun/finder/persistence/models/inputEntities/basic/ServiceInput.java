package cz.metacentrum.perun.finder.persistence.models.inputEntities.basic;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.BasicInputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.service.IncorrectSourceEntityException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perun.finder.persistence.enums.PerunEntityType.SERVICE;

public class ServiceInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = SERVICE;
	private static final String ENTITY_ID_FIELD = "service_id";
	private static final String ENTITY_TABLE = "services";
	private static final String ENTITY_ATTRS_TABLE = "service_required_attributes";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Collections.singletonList(RESOURCE);

	public ServiceInput(boolean isTopLevel, List<InputAttribute> core, List<InputEntity> innerInputs) throws IllegalRelationException {
		super(TYPE, isTopLevel, core, new ArrayList<>(), new ArrayList<>(), innerInputs);
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
			case RESOURCE:
				return getQueryForResource(isSimple);
			default:
				throw new IncorrectSourceEntityException("SERVICE cannot have " + sourceType + " as SourceType");
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "rs.resource_id AS foreign_id";
		String join = "JOIN resource_services rs ON rs.service_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}
}
