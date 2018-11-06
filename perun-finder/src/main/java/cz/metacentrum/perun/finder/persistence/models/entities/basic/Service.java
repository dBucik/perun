package cz.metacentrum.perun.finder.persistence.models.entities.basic;

import cz.metacentrum.perun.finder.persistence.enums.PerunEntityType;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;

/**
 * Model of Service entity.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class Service extends PerunEntity {

	private String name;
	private String description;
	private Integer delay;
	private Integer recurrence;
	private boolean enabled;
	private String script;

	public Service(Integer id, String name, String description, Integer delay, Integer recurrence, Boolean enabled,
				   String script, Integer foreignId) {
		super(id, foreignId, PerunEntityType.SERVICE);
		this.name = name;
		this.description = description;
		this.delay = delay;
		this.recurrence = recurrence;
		this.enabled = enabled;
		this.script = script;
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

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Integer getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Integer recurrence) {
		this.recurrence = recurrence;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
