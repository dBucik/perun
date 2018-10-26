package cz.metacentrum.perun.finder.persistence.models.entities.basic;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunRichEntity;

import java.util.Map;

public class UserExtSource extends PerunRichEntity {

	private Integer userId;
	private String loginExt;
	private Integer extSourceId;
	private Integer loa;
	private Long lastAccess;

	public UserExtSource(Integer id, Integer userId, String loginExt, Integer extSourceId,
						 Integer loa, Long lastAccess, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(id, attributes, foreignId);
		this.userId = userId;
		this.loginExt = loginExt;
		this.extSourceId = extSourceId;
		this.loa = loa;
		this.lastAccess = lastAccess;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginExt() {
		return loginExt;
	}

	public void setLoginExt(String loginExt) {
		this.loginExt = loginExt;
	}

	public Integer getExtSourceId() {
		return extSourceId;
	}

	public void setExtSourceId(Integer extSourceId) {
		this.extSourceId = extSourceId;
	}

	public Integer getLoa() {
		return loa;
	}

	public void setLoa(Integer loa) {
		this.loa = loa;
	}

	public Long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Long lastAccess) {
		this.lastAccess = lastAccess;
	}
}
