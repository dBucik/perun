package cz.metacentrum.perun.finder.service;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.ExtSourceInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.FacilityInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.GroupInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.HostInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.MemberInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.ResourceInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.ServiceInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.UserExtSourceInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.UserInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.VoInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.relations.GroupResourceInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.relations.MemberGroupInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.relations.MemberResourceInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.relations.UserFacilityInput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JsonToInputParser {

	public static InputEntity parseInput(String inputString) throws InputParseException, IllegalRelationException, AttributeTypeException {
		JSONObject json = new JSONObject(inputString);
		return parseInputEntity(json, true);
	}

	private static InputEntity parseInputEntity(JSONObject json, boolean isTopLevel) throws InputParseException, IllegalRelationException, AttributeTypeException {
		if (! json.has("entityName") || json.isNull("entityName")) {
			throw new InputParseException("Input does not have specified entity");
		}

		String entity = json.getString("entityName");
		boolean isSimpleEntity = PerunEntityType.isSimpleEntity(entity);

		JSONArray attributesJsonArray = (isSimpleEntity) ? null : json.optJSONArray("attributes");
		JSONArray attrsNamesJsonArray = (isSimpleEntity) ? null : json.optJSONArray("attributeNames");
		JSONArray entitiesJsonArray = json.optJSONArray("relations");

		List<InputAttribute> attributes = parseAttributes(attributesJsonArray);
		List<String> attrsNames = parseAttrsNames(attrsNamesJsonArray);
		List<InputEntity> entities = parseInnerInputs(entitiesJsonArray);

		return resolveEntity(isTopLevel, json, entity, attributes, attrsNames, entities);
	}

	private static InputEntity resolveEntity(boolean isTopLevel, JSONObject json, String entity, List<InputAttribute> attributes,
											 List<String> attrsNames, List<InputEntity> entities) throws IllegalRelationException, AttributeTypeException {
		switch (entity.toUpperCase()) {
			case "EXT_SOURCE": {
				List<InputAttribute> core = mapCoreExtSource(json);
				return new ExtSourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "FACILITY": {
				List<InputAttribute> core = mapCoreFacility(json);
				return new FacilityInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "GROUP": {
				List<InputAttribute> core = mapCoreGroup(json);
				return new GroupInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "HOST": {
				List<InputAttribute> core = mapCoreHost(json);
				return new HostInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER": {
				List<InputAttribute> core = mapCoreMember(json);
				return new MemberInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "RESOURCE": {
				List<InputAttribute> core = mapCoreResource(json);
				return new ResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "SERVICE": {
				List<InputAttribute> core = mapCoreService(json);
				return new ServiceInput(isTopLevel, core, entities);
			}
			case "USER_EXT_SOURCE": {
				List<InputAttribute> core = mapCoreUserExtSource(json);
				return new UserExtSourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "USER": {
				List<InputAttribute> core = mapCoreUser(json);
				return new UserInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "VO": {
				List<InputAttribute> core = mapCoreVo(json);
				return new VoInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "GROUP_RESOURCE": {
				List<InputAttribute> core = mapCoreGroupResource(json);
				return new GroupResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER_GROUP": {
				List<InputAttribute> core = mapCoreMemberGroup(json);
				return new MemberGroupInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER_RESOURCE": {
				List<InputAttribute> core = mapCoreMemberResource(json);
				return new MemberResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "USER_FACILITY": {
				List<InputAttribute> core = mapCoreUserFacility(json);
				return new UserFacilityInput(isTopLevel, core, attributes, attrsNames, entities);
			}
		}

		return null;
	}

	private static List<InputAttribute> mapCoreExtSource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("name")) {
			JSONObject attribute = json.getJSONObject("name");
			list.add(getInputAttr(attribute, "name"));
		}

		if (json.has("type")) {
			JSONObject attribute = json.getJSONObject("type");
			list.add(getInputAttr(attribute, "type"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreFacility(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("name")) {
			JSONObject attribute = json.getJSONObject("name");
			list.add(getInputAttr(attribute, "name"));
		}

		if (json.has("description")) {
			JSONObject attribute = json.getJSONObject("description");
			list.add(getInputAttr(attribute, "dsc"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreGroup(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("name")) {
			JSONObject attribute = json.getJSONObject("name");
			list.add(getInputAttr(attribute, "name"));
		}

		if (json.has("description")) {
			JSONObject attribute = json.getJSONObject("description");
			list.add(getInputAttr(attribute, "dsc"));
		}

		if (json.has("voId")) {
			JSONObject attribute = json.getJSONObject("voId");
			list.add(getInputAttr(attribute, "vo_id"));
		}

		if (json.has("parentGroupId")) {
			JSONObject attribute = json.getJSONObject("parentGroupId");
			list.add(getInputAttr(attribute, "parent_group_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreHost(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("hostname")) {
			JSONObject attribute = json.getJSONObject("hostname");
			list.add(getInputAttr(attribute, "hostname"));
		}

		if (json.has("facilityId")) {
			JSONObject attribute = json.getJSONObject("facilityId");
			list.add(getInputAttr(attribute, "facility_id"));
		}

		if (json.has("description")) {
			JSONObject attribute = json.getJSONObject("description");
			list.add(getInputAttr(attribute, "dsc"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreMember(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("userId")) {
			JSONObject attribute = json.getJSONObject("userId");
			list.add(getInputAttr(attribute, "user_id"));
		}

		if (json.has("voId")) {
			JSONObject attribute = json.getJSONObject("voId");
			list.add(getInputAttr(attribute, "vo_id"));
		}

		if (json.has("sponsored")) {
			JSONObject attribute = json.getJSONObject("sponsored");
			list.add(getInputAttr(attribute, "sponsored"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreResource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("facilityId")) {
			JSONObject attribute = json.getJSONObject("facilityId");
			list.add(getInputAttr(attribute, "facility_id"));
		}

		if (json.has("name")) {
			JSONObject attribute = json.getJSONObject("name");
			list.add(getInputAttr(attribute, "name"));
		}

		if (json.has("description")) {
			JSONObject attribute = json.getJSONObject("description");
			list.add(getInputAttr(attribute, "dsc"));
		}

		if (json.has("voId")) {
			JSONObject attribute = json.getJSONObject("voId");
			list.add(getInputAttr(attribute, "vo_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreService(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("name")) {
			JSONObject attribute = json.getJSONObject("name");
			list.add(getInputAttr(attribute, "name"));
		}

		if (json.has("description")) {
			JSONObject attribute = json.getJSONObject("description");
			list.add(getInputAttr(attribute, "description"));
		}

		if (json.has("delay")) {
			JSONObject attribute = json.getJSONObject("delay");
			list.add(getInputAttr(attribute, "delay"));
		}

		if (json.has("recurrence")) {
			JSONObject attribute = json.getJSONObject("recurrence");
			list.add(getInputAttr(attribute, "recurrence"));
		}

		if (json.has("enabled")) {
			JSONObject attribute = json.getJSONObject("enabled");
			InputAttribute attr = getInputAttrWithTransformationFromBooleanToString(attribute, "enabled");
			list.add(attr);
		}

		if (json.has("script")) {
			JSONObject attribute = json.getJSONObject("script");
			list.add(getInputAttr(attribute, "script"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreUserExtSource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("userId")) {
			JSONObject attribute = json.getJSONObject("userId");
			list.add(getInputAttr(attribute, "user_id"));
		}

		if (json.has("loginExt")) {
			JSONObject attribute = json.getJSONObject("loginExt");
			list.add(getInputAttr(attribute, "login_ext"));
		}

		if (json.has("extSourcesId")) {
			JSONObject attribute = json.getJSONObject("extSourcesId");
			list.add(getInputAttr(attribute, "ext_sources_id"));
		}

		if (json.has("loa")) {
			JSONObject attribute = json.getJSONObject("loa");
			list.add(getInputAttr(attribute, "loa"));
		}

		if (json.has("lastAccess")) {
			JSONObject attribute = json.getJSONObject("lastAccess");
			list.add(getInputAttrWithTransformationFromStringToTimestamp(attribute, "last_access"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreUser(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("firstName")) {
			JSONObject attribute = json.getJSONObject("firstName");
			list.add(getInputAttr(attribute, "first_name"));
		}

		if (json.has("middleName")) {
			JSONObject attribute = json.getJSONObject("middleName");
			list.add(getInputAttr(attribute, "middle_name"));
		}

		if (json.has("lastName")) {
			JSONObject attribute = json.getJSONObject("lastName");
			list.add(getInputAttr(attribute, "last_name"));
		}

		if (json.has("titleBefore")) {
			JSONObject attribute = json.getJSONObject("titleBefore");
			list.add(getInputAttr(attribute, "title_before"));
		}

		if (json.has("titleAfter")) {
			JSONObject attribute = json.getJSONObject("titleAfter");
			list.add(getInputAttr(attribute, "title_after"));
		}

		if (json.has("serviceAcc")) {
			JSONObject attribute = json.getJSONObject("serviceAcc");
			InputAttribute attr = getInputAttrWithTransformationFromBooleanToString(attribute, "service_acc");
			list.add(attr);
		}

		if (json.has("sponsoredAcc")) {
			JSONObject attribute = json.getJSONObject("sponsoredAcc");
			InputAttribute attr = getInputAttrWithTransformationFromBooleanToString(attribute, "sponsored_acc");
			list.add(attr);
		}

		return list;
	}

	private static List<InputAttribute> mapCoreVo(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			JSONObject attribute = json.getJSONObject("id");
			list.add(getInputAttr(attribute, "id"));
		}

		if (json.has("name")) {
			JSONObject attribute = json.getJSONObject("name");
			list.add(getInputAttr(attribute, "name"));
		}

		if (json.has("shortName")) {
			JSONObject attribute = json.getJSONObject("shortName");
			list.add(getInputAttr(attribute, "short_name"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreGroupResource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("groupId")) {
			JSONObject attribute = json.getJSONObject("groupId");
			list.add(getInputAttr(attribute, "group_id"));
		}

		if (json.has("resourceId")) {
			JSONObject attribute = json.getJSONObject("resourceId");
			list.add(getInputAttr(attribute, "resource_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreMemberGroup(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("memberId")) {
			JSONObject attribute = json.getJSONObject("memberId");
			list.add(getInputAttr(attribute, "member_id"));
		}

		if (json.has("groupId")) {
			JSONObject attribute = json.getJSONObject("groupId");
			list.add(getInputAttr(attribute, "group_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreMemberResource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("memberId")) {
			JSONObject attribute = json.getJSONObject("memberId");
			list.add(getInputAttr(attribute, "member_id"));
		}

		if (json.has("resourceId")) {
			JSONObject attribute = json.getJSONObject("resourceId");
			list.add(getInputAttr(attribute, "resource_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreUserFacility(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("userId")) {
			JSONObject attribute = json.getJSONObject("userId");
			list.add(getInputAttr(attribute, "user_id"));
		}

		if (json.has("facilityId")) {
			JSONObject attribute = json.getJSONObject("facilityId");
			list.add(getInputAttr(attribute, "facility_id"));
		}

		return list;
	}

	private static List<InputAttribute> parseAttributes(JSONArray attrsJsonArray) throws InputParseException {
		List<InputAttribute> attributes = new ArrayList<>();
		if (attrsJsonArray != null) {
			for (int i = 0; i < attrsJsonArray.length(); i++) {
				JSONObject attributeJson = attrsJsonArray.getJSONObject(i);
				InputAttribute attribute = parseAttribute(attributeJson);
				attributes.add(attribute);
			}
		}

		return attributes;
	}

	private static List<String> parseAttrsNames(JSONArray attrsNamesJsonArray) {
		List<String> attrsNames = new ArrayList<>();
		if (attrsNamesJsonArray != null) {
			for (int i = 0; i < attrsNamesJsonArray.length(); i++) {
				attrsNames.add(attrsNamesJsonArray.getString(i));
			}
		}

		return attrsNames;
	}

	private static List<InputEntity> parseInnerInputs(JSONArray entitiesJsonArray) throws IllegalRelationException, InputParseException, AttributeTypeException {
		List<InputEntity> entities = new ArrayList<>();
		if (entitiesJsonArray != null) {
			for (int i = 0; i < entitiesJsonArray.length(); i++) {
				JSONObject entityJson = entitiesJsonArray.getJSONObject(i);
				entities.add(parseInputEntity(entityJson, false));
			}
		}

		return entities;
	}

	private static InputAttribute parseAttribute(JSONObject attributeJson) throws InputParseException {
		String name = attributeJson.getString("name");
		boolean match = attributeJson.optBoolean("matchLike", false);
		JSONArray value = attributeJson.getJSONArray("value");

		try {
			return new InputAttribute(name, match, value);
		} catch (AttributeTypeException e) {
			throw new InputParseException("Input attribute cannot be parsed", e);
		}
	}

	private static InputAttribute getInputAttr(JSONObject attrJson, String keyInTable) throws AttributeTypeException {
		boolean match = attrJson.optBoolean("matchLike", false);
		JSONArray value = attrJson.getJSONArray("value");

		return new InputAttribute(keyInTable, match, value);
	}

	private static InputAttribute getInputAttrWithTransformationFromBooleanToString(JSONObject attrJson, String keyInTable) throws AttributeTypeException {
		boolean match = attrJson.optBoolean("matchLike", false);
		JSONArray value = attrJson.getJSONArray("value");
		JSONArray transformedValues = new JSONArray();
		for (int i = 0; i < value.length(); i++) {
			if (value.get(i) == JSONObject.NULL) {
				transformedValues.put(JSONObject.NULL);
			} else if (value.getBoolean(i)) {
				transformedValues.put("1");
			} else {
				transformedValues.put("0");
			}
		}

		return new InputAttribute(keyInTable, match, transformedValues);
	}

	private static InputAttribute getInputAttrWithTransformationFromStringToTimestamp(JSONObject attrJson, String keyInTable) throws AttributeTypeException {
		boolean match = attrJson.optBoolean("matchLike", false);
		JSONArray value = attrJson.getJSONArray("value");
		if (! match) {
			JSONArray transformedValues = new JSONArray();
			for (int i = 0; i < value.length(); i++) {
				if (value.get(i) == JSONObject.NULL) {
					transformedValues.put(JSONObject.NULL);
				} else {
					String val = value.getString(i);
					transformedValues.put(Timestamp.valueOf(val));
				}
			}

			return new InputAttribute(keyInTable, false, transformedValues);
		}

		return new InputAttribute(keyInTable, true, value);
	}
}
