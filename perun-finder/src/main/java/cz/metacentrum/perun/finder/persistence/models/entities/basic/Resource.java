package cz.metacentrum.perun.finder.persistence.models.entities.basic;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;

import java.util.Map;

public class Resource extends PerunRichEntity {

	private Integer facilityId;
	private String name;
	private String description;
	private Integer voId;

	public Resource(Integer id, Integer facilityId, String name, String description, Integer voId, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(id, attributes, foreignId);
		this.facilityId = facilityId;
		this.name = name;
		this.description = description;
		this.voId = voId;
	}

	public Integer getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Integer facilityId) {
		this.facilityId = facilityId;
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
}
