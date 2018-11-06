package cz.metacentrum.perun.finder.persistence.models;

import cz.metacentrum.perun.finder.persistence.enums.InputAttributeType;
import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Attribute obtained as input from user in query.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class InputAttribute {

	private String name;
	private InputAttributeType type;
	private boolean likeMatch;
	private JSONArray value;

	public InputAttribute(String name, boolean likeMatch, JSONArray value) throws AttributeTypeException {
		this.name = name;
		this.type = getType(value);
		this.likeMatch = likeMatch;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputAttributeType getType() {
		return type;
	}

	public void setType(InputAttributeType type) {
		this.type = type;
	}

	public void setValue(JSONArray value) {
		this.value = value;
	}

	public List<Object> getValue() {
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			values.add(value.get(i));
		}

		return values;
	}

	public boolean isLikeMatch() {
		return likeMatch;
	}

	public void setLikeMatch(boolean likeMatch) {
		this.likeMatch = likeMatch;
	}

	/**
	 * Get value as list of Strings
	 * @return List of Strings / empty list
	 */
	public List<String> valueAsStrings() {
		List<String> values = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			values.add(value.getString(i));
		}

		return values;
	}

	/**
	 * Get value as List of Integers
	 * @return List of Integers / empty list
	 */
	public List<Integer> valueAsInts() {
		List<Integer> values = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			values.add(value.getInt(i));
		}

		return values;
	}

	/**
	 * Get values as List of Booleans
	 * @return List of Booleans / empty list
	 */
	public List<Boolean> valueAsBooleans() {
		List<Boolean> values = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			values.add(value.getBoolean(i));
		}

		return values;
	}

	/**
	 * Get values as List of Lists<String>
	 * @return List of values / empty list
	 */
	public List<List<String>> valueAsLists() {
		List<List<String>> values = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			JSONArray sub = value.getJSONArray(i);
			List<String> subValues = new ArrayList<>();
			for (int j = 0; j < sub.length(); j++) {
				subValues.add(sub.get(i).toString());
			}
			values.add(subValues);
		}
		return values;
	}

	/**
	 * Get values as List of Map<String, String>
	 * @return List of values / empty list
	 */
	public List<Map<String, String>> valueAsMaps() {
		List<Map<String, String>> values = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			JSONObject obj =  value.getJSONObject(i);
			Iterator<String> keys = obj.keys();
			Map<String, String> subValues = new LinkedHashMap<>();
			while (keys.hasNext()) {
				String key = keys.next();
				String val = obj.getString(key);
				subValues.put(key, val);
			}
			values.add(subValues);
		}

		return values;
	}

	private InputAttributeType getType(JSONArray value) throws AttributeTypeException {
		if (value != null && value.length() > 0) {
			Object part = value.get(0);
			if (part instanceof String) {
				return InputAttributeType.STRING;
			} else if (part instanceof Number) {
				return InputAttributeType.INTEGER;
			} else if (part instanceof Boolean) {
				return InputAttributeType.BOOLEAN;
			} else if (part instanceof JSONArray) {
				return InputAttributeType.ARRAY;
			} else if (part instanceof JSONObject) {
				return InputAttributeType.MAP;
			} else if (part == JSONObject.NULL) {
				return InputAttributeType.NULL;
			} else if (part instanceof Timestamp) {
				return InputAttributeType.TIMESTAMP;
			}
		}

		throw new AttributeTypeException("Attribute cannot have type: " + type);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		if (this.name != null) {
			hash *= 31 * this.name.hashCode();
		}

		if (this.type != null) {
			hash *= 31 * this.type.hashCode();
		}

		if (this.value != null) {
			hash *= 31 * this.value.hashCode();
		}

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (! (o instanceof InputAttribute)) {
			return false;
		} else {
			InputAttribute them = (InputAttribute) o;
			return Objects.equals(this.name, them.name)
					&& Objects.equals(this.type, them.type)
					&& compareJsonArrays(this.value, them.value);
		}
	}

	private boolean compareJsonArrays(JSONArray a1, JSONArray a2) {
		if (a1 == null || a2 == null) {
			return a1 == a2;
		}

		List<Object> arr1 = new ArrayList<>();
		List<Object> arr2 = new ArrayList<>();
		for (int i = 0; i < a1.length(); i++) {
			arr1.add(a1.get(i));
		}
		for (int i = 0; i < a2.length(); i++) {
			arr2.add(a2.get(i));
		}

		return CollectionUtils.isEqualCollection(arr1, arr2);
	}
}
