package cz.metacentrum.perun.finder.persistence.models.entities.basic;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;

import java.util.Map;

public class Host extends PerunRichEntity {

	private String hostname;
	private Integer facilityId;
	private String description;

	public Host(Integer id, String hostname, Integer facilityId, String description, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(id, attributes, foreignId);
		this.hostname = hostname;
		this.facilityId = facilityId;
		this.description = description;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Integer facilityId) {
		this.facilityId = facilityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
