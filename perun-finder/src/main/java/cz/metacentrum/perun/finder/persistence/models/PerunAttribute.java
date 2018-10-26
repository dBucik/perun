package cz.metacentrum.perun.finder.persistence.models;

import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Attribute stored in Perun.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class PerunAttribute {

	private String name;
	private PerunAttributeType type;
	private String value;

	public PerunAttribute(String name, String type, String value) throws AttributeTypeException {
		this.name = name;
		this.type = PerunAttributeType.fromString(type);
		this.value = value;
	}

	public PerunAttribute(String name, PerunAttributeType type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PerunAttributeType getType() {
		return type;
	}

	public void setType(PerunAttributeType type) {
		this.type = type;
	}

	public void setType(String type) throws AttributeTypeException {
		this.type = PerunAttributeType.fromString(type);
	}

	public void setValue(String value) {
		this.value = value;
	}

	@JsonProperty(value = "value")
	public Object getValue() {
		switch (this.type) {
			case STRING:
			case LARGE_STRING:
				return valueAsString();
			case INTEGER: return valueAsInt();
			case BOOLEAN: return valueAsBoolean();
			case ARRAY:
			case LARGE_ARRAY_LIST:
				return valueAsList();
			case MAP: return valueAsMap();
			default: return null;
		}
	}

	/**
	 * Return value as String.
	 * @return String or NULL if type does not match.
	 */
	public String valueAsString() {
		return (type == PerunAttributeType.STRING
				|| type == PerunAttributeType.LARGE_STRING) ? this.value : null;
	}

	/**
	 * Return value as Integer.
	 * @return Integer or NULL if type does not match.
	 */
	public Integer valueAsInt() {
		return (type == PerunAttributeType.INTEGER) ? Integer.parseInt(value) : null;
	}

	/**
	 * Return value as Boolean.
	 * @return Boolean or NULL if type does not match.
	 */
	public Boolean valueAsBoolean() {
		return (type == PerunAttributeType.BOOLEAN) ? Boolean.parseBoolean(value) : null;
	}

	/**
	 * Return value as List of Strings.
	 * @return List of Strings or NULL if type does not match.
	 */
	public List<String> valueAsList() {
		if (type != PerunAttributeType.ARRAY && type != PerunAttributeType.LARGE_ARRAY_LIST) {
			return null;
		}

		String[] parts = value.split("(?<!\\\\)(?:\\\\\\\\)*,");
		List<String> arr = new ArrayList<>();
		for (String s: parts) {
			arr.add(s.replace("\\,", ","));
		}

		return arr;
	}

	/**
	 * Return value as Map.
	 * @return Map (key = String, value = String) or NULL if type does not match.
	 */
	public Map<String, String> valueAsMap() {
		if (type != PerunAttributeType.MAP) {
			return null;
		}

		String[] parts = value.split("(?<!\\\\)(?:\\\\\\\\)*,");
		Map<String, String> valuesMap = new LinkedHashMap<>();
		for (String part: parts) {
			part = part.replace("\\,", ",");
			String[] subParts = part.split("(?<!\\\\)(?:\\\\\\\\)*:");
			String key = subParts[0].replace("\\:", ":");
			String value = subParts[1].replace("\\:", ":");
			valuesMap.put(key, value);
		}

		return valuesMap;
	}

	public String stringValue() {
		return value;
	}
}
