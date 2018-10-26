package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Host;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class HostMapper implements RowMapper<Host> {

	@Override
	public Host mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		String hostName = MappersUtils.getString(entityJson,"hostname");
		Integer facilityId = MappersUtils.getInteger(entityJson, "facility_id");
		String dsc = MappersUtils.getString(entityJson,"dsc");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new Host(id, hostName, facilityId, dsc, attributes, foreignId);
	}
}
