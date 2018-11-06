package cz.metacentrum.perun.finder.persistence.models.entities;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;

/**
 * General model of Entity from Perun.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
@JsonIgnoreProperties(value = {"foreignId"})
public abstract class PerunEntity {

	private Integer id;
	@JsonIgnore
	private Integer foreignId;
	@JsonIgnore
	private final PerunEntityType type;

	@JsonProperty("entityType")
	private final String entityType;

	protected PerunEntity(Integer id, Integer foreignId, PerunEntityType type) {
		this.id = id;
		this.foreignId = foreignId;
		this.type = type;
		this.entityType = this.type.toString();
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Integer getForeignId() {
		return foreignId;
	}

	public void setForeignId(Integer foreignId) {
		this.foreignId = foreignId;
	}

	public String getEntityType() {
		return entityType;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PerunEntity)) {
			return false;
		} else if (! this.getClass().equals(o.getClass())) {
			return false;
		}

		PerunEntity them = (PerunEntity) o;

		return Objects.equals(this.id, them.id);
	}
}
