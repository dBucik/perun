package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Group;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GroupMapper implements RowMapper<Group> {

	@Override
	public Group mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		String name = MappersUtils.getString(entityJson,"name");
		String dsc = MappersUtils.getString(entityJson,"dsc");
		Integer voId = MappersUtils.getInteger(entityJson, "vo_id");
		Integer parentGroupId = MappersUtils.getInteger(entityJson, "parent_group_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new Group(id, name, dsc, voId, parentGroupId, attributes, foreignId);
	}
}
