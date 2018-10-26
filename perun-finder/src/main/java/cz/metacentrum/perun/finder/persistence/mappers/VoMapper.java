package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Vo;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class VoMapper implements RowMapper<Vo> {

	@Override
	public Vo mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		String name = MappersUtils.getString(entityJson, "name");
		String shortName = MappersUtils.getString(entityJson, "short_name");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new Vo(id, name, shortName, attributes, foreignId);
	}
}
