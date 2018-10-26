package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.relations.MemberResource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MemberResourceMapper implements RowMapper<MemberResource> {

	@Override
	public MemberResource mapRow(ResultSet resultSet, int i) throws SQLException {
		Integer memberId = resultSet.getInt("member_id");
		Integer resourceId = resultSet.getInt("resource_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new MemberResource(memberId, resourceId, attributes, foreignId);
	}
}
