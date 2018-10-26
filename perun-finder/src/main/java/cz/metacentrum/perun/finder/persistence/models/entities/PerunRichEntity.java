package cz.metacentrum.perun.finder.persistence.models.entities;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;

import java.util.HashMap;
import java.util.Map;

public abstract class PerunRichEntity extends PerunEntity {

	private Map<String, PerunAttribute> attributes = new HashMap<>();

	protected PerunRichEntity(Integer id, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(id, foreignId);
		if (attributes != null) {
			this.attributes.putAll(attributes);
		}
	}

	public Map<String, PerunAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, PerunAttribute> attributes) {
		this.attributes.putAll(attributes);
	}

}
