package cz.metacentrum.perun.finder.persistence.models.entities.basic;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;

import java.util.Map;

public class Member extends PerunRichEntity {

	private Integer userId;
	private Integer voId;
	private Boolean sponsored;

	public Member(Integer id, Integer userId, Integer voId, Boolean sponsored, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(id, attributes, foreignId);
		this.userId = userId;
		this.voId = voId;
		this.sponsored = sponsored;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getVoId() {
		return voId;
	}

	public void setVoId(Integer voId) {
		this.voId = voId;
	}

	public Boolean isSponsored() {
		return sponsored;
	}

	public void setSponsored(Boolean sponsored) {
		this.sponsored = sponsored;
	}
}
