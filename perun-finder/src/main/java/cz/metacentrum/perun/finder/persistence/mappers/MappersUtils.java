package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class MappersUtils {

	public static Integer getForeignId(ResultSet resultSet) throws SQLException {
		Integer foreignId;
		try {
			resultSet.findColumn("foreign_id");
		} catch (PSQLException e) {
			//this is fine
			return null;
		}

		foreignId = resultSet.getInt("foreign_id");
		return foreignId;
	}

	public static Map<String, PerunAttribute> getAttributes(ResultSet resultSet) throws SQLException {
		Map<String, PerunAttribute> attributes;
		try {
			JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
			attributes = mapAttributes(attributesJson, null);
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		return attributes;
	}

	public static Map<String, PerunAttribute> getAttributes(ResultSet resultSet, String defaultType) throws SQLException {
		Map<String, PerunAttribute> attributes;
		try {
			JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
			attributes = mapAttributes(attributesJson, defaultType);
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		return attributes;
	}

	public static String getString(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getString(key) : null;
	}

	public static Boolean getBoolean(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getBoolean(key) : null;
	}

	public static Integer getInteger(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getInt(key) : null;
	}

	public static Integer getInt(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getInt(key) : null;
	}

	public static Long getTimestampMillis(JSONObject json, String key) {
		if (json.has(key) && json.get(key) != JSONObject.NULL) {
			LocalDateTime time = LocalDateTime.parse(json.getString(key));
			return time.toEpochSecond(ZoneOffset.UTC);
		}
		return null;
	}

	private static Map<String, PerunAttribute> mapAttributes(JSONArray json, String defaultType) throws AttributeTypeException {
		Map<String, PerunAttribute> result = new HashMap<>();
		for (int i = 0; i < json.length(); i++) {
			JSONObject attribute = json.getJSONObject(i);
			String name = attribute.getString("name");
			String type =  attribute.optString("type", defaultType);
			String value = attribute.optString("value", null);

			result.put(name, new PerunAttribute(name, type, value));
		}

		return result;
	}

}
