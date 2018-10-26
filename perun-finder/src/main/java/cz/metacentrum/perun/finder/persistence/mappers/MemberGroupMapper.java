package cz.metacentrum.perun.finder.persistence.mappers;

import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.relations.MemberGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MemberGroupMapper implements RowMapper<MemberGroup> {

	@Override
	public MemberGroup mapRow(ResultSet resultSet, int i) throws SQLException {
		Integer memberId = resultSet.getInt("member_id");
		Integer groupId = resultSet.getInt("group_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new MemberGroup(memberId, groupId, attributes, foreignId);
	}
}
