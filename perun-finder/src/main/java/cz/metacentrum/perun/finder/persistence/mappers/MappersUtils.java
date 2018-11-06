package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for different mappers.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class MappersUtils {

	private static final Logger log = LoggerFactory.getLogger(MappersUtils.class);

	/**
	 * Get ForeignID field
	 * @param resultSet resultSet containing values
	 * @return ForeignID or null
	 * @throws SQLException when problem with SQL occurs.
	 */
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

	/**
	 * Parse attributes
	 * @param resultSet resultSet containing values
	 * @return Mapped attributes in Map attrName=>attribute or null
	 * @throws SQLException when problem with SQL occurs.
	 */
	public static Map<String, PerunAttribute> getAttributes(ResultSet resultSet) throws SQLException {
		Map<String, PerunAttribute> attributes;
		try {
			String attributesString = resultSet.getString("attributes");
			if (resultSet.wasNull()) {
				return null;
			}
			JSONArray attributesJson = new JSONArray(attributesString);
			attributes = mapAttributes(attributesJson, null);
		} catch (AttributeTypeException e) {
			log.error("Error while parsing attributes, attribute type mismatch", e);
			return null;
		}

		return attributes;
	}

	/**
	 * Parse attributes
	 * @param resultSet resultSet containing values
	 * @param defaultType default type used for attribute
	 * @return Mapped attributes in Map attrName=>attribute or null
	 * @throws SQLException when problem with SQL occurs.
	 */
	public static Map<String, PerunAttribute> getAttributes(ResultSet resultSet, String defaultType) throws SQLException {
		Map<String, PerunAttribute> attributes;
		try {
			String attributesString = resultSet.getString("attributes");
			if (resultSet.wasNull()) {
				return null;
			}
			JSONArray attributesJson = new JSONArray(attributesString);
			attributes = mapAttributes(attributesJson, defaultType);
		} catch (AttributeTypeException e) {
			log.error("Error while parsing attributes, attribute type mismatch", e);
			return null;
		}

		return attributes;
	}

	/**
	 * Get String value from json
	 * @param json json
	 * @param key key of the value
	 * @return value or null
	 */
	public static String getString(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getString(key) : null;
	}

	/**
	 * Get Boolean value from json
	 * @param json json
	 * @param key key of the value
	 * @return value or null
	 */
	public static Boolean getBoolean(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getBoolean(key) : null;
	}

	/**
	 * Get Integer value from json
	 * @param json json
	 * @param key key of the value
	 * @return value or null
	 */
	public static Integer getInteger(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getInt(key) : null;
	}

	/**
	 * Get Timestamp milliseconds value from json
	 * @param json json
	 * @param key key of the value
	 * @return value or null
	 */
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
