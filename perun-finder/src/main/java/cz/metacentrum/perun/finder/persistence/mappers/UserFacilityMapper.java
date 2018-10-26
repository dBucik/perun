package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.relations.UserFacility;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserFacilityMapper implements RowMapper<UserFacility> {

	@Override
	public UserFacility mapRow(ResultSet resultSet, int i) throws SQLException {
		Integer userId = resultSet.getInt("user_id");
		Integer facilityId = resultSet.getInt("facility_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new UserFacility(userId, facilityId, attributes, foreignId);
	}
}
