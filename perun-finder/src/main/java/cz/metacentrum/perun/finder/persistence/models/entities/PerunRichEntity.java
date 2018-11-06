package cz.metacentrum.perun.finder.persistence.models.entities;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * Extended model of Entity from Perun, contains attributes.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public abstract class PerunRichEntity extends PerunEntity {

	private Map<String, PerunAttribute> attributes = new HashMap<>();

	protected PerunRichEntity(Integer id, Map<String, PerunAttribute> attributes, Integer foreignId, PerunEntityType type) {
		super(id, foreignId, type);
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
