package cz.metacentrum.perun.finder.persistence.models.entities.basic;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;

import java.util.Map;

/**
 * Model of Group entity.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class Group extends PerunRichEntity {

	private String name;
	private String description;
	private Integer voId;
	private Integer parentGroupId;

	public Group(Integer id, String name, String description, Integer voId, Integer parentGroupId, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(id, attributes, foreignId, PerunEntityType.GROUP);
		this.name = name;
		this.description = description;
		this.voId = voId;
		this.parentGroupId = parentGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getVoId() {
		return voId;
	}

	public void setVoId(Integer voId) {
		this.voId = voId;
	}

	public Integer getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(Integer parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
}
