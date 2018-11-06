package cz.metacentrum.perun.finder.persistence.models.entities.relations;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;
import java.util.Objects;

/**
 * Model of Member Resource relation.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
@JsonIgnoreProperties(value = "id")
public class MemberResource extends PerunRichEntity {

	private Integer memberId;
	private Integer resourceId;

	public MemberResource(Integer memberId, Integer resourceId, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(null, attributes, foreignId, PerunEntityType.MEMBER_RESOURCE);
		this.memberId = memberId;
		this.resourceId = resourceId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MemberResource)) {
			return false;
		}

		MemberResource them = (MemberResource) o;

		return Objects.equals(this.memberId, them.memberId)
				&& Objects.equals(this.resourceId, them.resourceId);
	}
}
