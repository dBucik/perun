package cz.metacentrum.perun.finder.persistence.models.entities.relations;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(value = "id")
public class UserFacility extends PerunRichEntity {

	private Integer userId;
	private Integer facilityId;

	public UserFacility(Integer userId, Integer facilityId, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(null, attributes, foreignId);
		this.userId = userId;
		this.facilityId = facilityId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Integer resourceId) {
		this.facilityId = resourceId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof UserFacility)) {
			return false;
		}

		UserFacility them = (UserFacility) o;

		return Objects.equals(this.userId, them.userId)
				&& Objects.equals(this.userId, them.userId);
	}
}
