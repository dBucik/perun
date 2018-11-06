package cz.metacentrum.perun.finder.persistence.models.entities.relations;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;
import java.util.Objects;

/**
 * Model of Group Resource relation.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
@JsonIgnoreProperties(value = "id")
public class GroupResource extends PerunRichEntity {

	private Integer groupId;
	private Integer resourceId;

	public GroupResource(Integer groupId, Integer resourceId, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(null, attributes, foreignId, PerunEntityType.GROUP_RESOURCE);
		this.groupId = groupId;
		this.resourceId = resourceId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GroupResource)) {
			return false;
		}

		GroupResource them = (GroupResource) o;

		return Objects.equals(this.groupId, them.groupId)
				&& Objects.equals(this.resourceId, them.resourceId);
	}
}
