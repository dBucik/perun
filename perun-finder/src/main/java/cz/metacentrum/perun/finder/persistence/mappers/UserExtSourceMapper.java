package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.UserExtSource;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserExtSourceMapper implements RowMapper<UserExtSource> {

	@Override
	public UserExtSource mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		Integer userId = MappersUtils.getInteger(entityJson, "user_id");
		String loginExt = MappersUtils.getString(entityJson,"login_ext");
		Integer extSourceId = MappersUtils.getInteger(entityJson, "ext_source_id");
		Integer loa = MappersUtils.getInt(entityJson, "loa");
		Long lastAccess = MappersUtils.getTimestampMillis(entityJson, "last_access");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new UserExtSource(id, userId, loginExt, extSourceId, loa, lastAccess, attributes, foreignId);
	}
}
