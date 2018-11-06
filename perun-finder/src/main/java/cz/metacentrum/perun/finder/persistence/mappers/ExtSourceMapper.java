package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.ExtSource;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Mapper for the ExtSource.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class ExtSourceMapper implements RowMapper<ExtSource> {

	@Override
	public ExtSource mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		String name = MappersUtils.getString(entityJson,"name");
		String type = MappersUtils.getString(entityJson,"type");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet, PerunAttributeType.STRING_TYPE);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new ExtSource(id, name, type, attributes, foreignId);
	}
}
