package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.relations.GroupResource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GroupResourceMapper implements RowMapper<GroupResource> {

	@Override
	public GroupResource mapRow(ResultSet resultSet, int i) throws SQLException {
		Integer groupId = resultSet.getInt("group_id");
		Integer resourceId = resultSet.getInt("resource_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new GroupResource(groupId, resourceId, attributes, foreignId);
	}
}
