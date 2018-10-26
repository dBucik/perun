package cz.metacentrum.perun.finder.persistence.enums;

public enum PerunEntityType {

	EXT_SOURCE,
	FACILITY,
	GROUP,
	HOST,
	MEMBER,
	RESOURCE,
	SERVICE,
	USER_EXT_SOURCE,
	USER,
	VO,
	GROUP_RESOURCE,
	MEMBER_GROUP,
	MEMBER_RESOURCE,
	USER_FACILITY;

	public static boolean isRelation(PerunEntityType entityType) {
		return entityType == GROUP_RESOURCE ||
				entityType == MEMBER_GROUP ||
				entityType == MEMBER_RESOURCE ||
				entityType == USER_FACILITY;
	}

	public static boolean isSimpleEntity(PerunEntityType entityType) {
		return entityType == SERVICE;
	}

	public static boolean isSimpleEntity(String entity) {
		return "SERVICE".equalsIgnoreCase(entity);
	}
}
