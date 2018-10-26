package cz.metacentrum.perun.finder.persistence.enums;

import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;

/**
 * Class represents type of attributes in Perun.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public enum PerunAttributeType {

	STRING,
	INTEGER,
	BOOLEAN,
	ARRAY,
	MAP,
	LARGE_STRING,
	LARGE_ARRAY_LIST;

	public final static String STRING_TYPE = "java.lang.String";
	private final static String INTEGER_TYPE = "java.lang.Integer";
	private final static String BOOLEAN_TYPE = "java.lang.Boolean";
	private final static String ARRAY_TYPE = "java.util.ArrayList";
	private final static String MAP_TYPE = "java.util.LinkedHashMap";
	private final static String LARGE_STRING_TYPE = "java.lang.LargeString";
	private final static String LARGE_ARRAY_LIST_TYPE = "java.lang.LargeArrayList";

	/**
	 * Get type of attribute from provided string. The type has to be fully specified class
	 * (with all packages, eg: java.lang:String).
	 * Allowed types are: String, Integer, Boolean, ArrayList, LinkedHashMap, LargeString, LargeArrayList.
	 * @param type String representation of type.
	 * @return Parsed type.
	 * @throws AttributeTypeException When the input does not match allowed values.
	 */
	public static PerunAttributeType fromString(String type) throws AttributeTypeException {
		switch (type) {
			case STRING_TYPE: return STRING;
			case INTEGER_TYPE: return INTEGER;
			case BOOLEAN_TYPE: return BOOLEAN;
			case ARRAY_TYPE: return ARRAY;
			case MAP_TYPE: return MAP;
			case LARGE_STRING_TYPE: return LARGE_STRING;
			case LARGE_ARRAY_LIST_TYPE: return LARGE_ARRAY_LIST;
		}

		throw new AttributeTypeException("Attribute cannot have type: " + type);
	}

	/**
	 * Convert type to String representation.
	 * @return String representation, NULL if does not match allowed values.
	 */
	@Override
	public String toString() {
		switch (this) {
			case STRING: return STRING_TYPE;
			case INTEGER: return INTEGER_TYPE;
			case BOOLEAN: return BOOLEAN_TYPE;
			case ARRAY: return ARRAY_TYPE;
			case MAP: return MAP_TYPE;
			case LARGE_STRING: return LARGE_STRING_TYPE;
			case LARGE_ARRAY_LIST: return LARGE_ARRAY_LIST_TYPE;
		}

		return null;
	}
}
