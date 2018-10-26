package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.FinderDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Group;
import cz.metacentrum.perun.finder.service.FinderManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class GroupSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static FinderManager finderManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Group EXPECTED1;
	private Group EXPECTED2;
	private Group EXPECTED23;
	private Group EXPECTED123;

	@BeforeClass
	public static void setUpDatabase() throws Exception {
		DataSource ds = pg.getEmbeddedPostgres().getPostgresDatabase();
		DBUtils.setUpDatabaseTables(ds, tablesFile, dataFile);
		JdbcTemplate template = new JdbcTemplate(ds);
		FinderDAOImpl dao = new FinderDAOImpl();
		dao.setTemplate(template);
		finderManager = new FinderManager(dao);
	}

	@Before
	public void setUp() {
		setUpGroup1();
		setUpGroup2();
		setUpGroup23();
		setUpGroup123();
	}

	private void setUpGroup1() {
		PerunAttribute group1attr1 = new PerunAttribute("group_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute group1attr2 = new PerunAttribute("group_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute group1attr3 = new PerunAttribute("group_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute group1attr4 = new PerunAttribute("group_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute group1attr5 = new PerunAttribute("group_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute group1attr6 = new PerunAttribute("group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute group1attr7 = new PerunAttribute("group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_attr_str", group1attr1);
		attributes.put("group_attr_int", group1attr2);
		attributes.put("group_attr_bool", group1attr3);
		attributes.put("group_attr_array", group1attr4);
		attributes.put("group_attr_map", group1attr5);
		attributes.put("group_attr_lstring", group1attr6);
		attributes.put("group_attr_larray", group1attr7);

		Integer id = 1;
		String name = "group1";
		String description = "dsc1";
		Integer voId = 1;
		Integer parentGroupId = null;

		EXPECTED1 = new Group(id, name, description, voId, parentGroupId, attributes, null);
	}

	private void setUpGroup2() {
		PerunAttribute group2attr1 = new PerunAttribute("group_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute group2attr2 = new PerunAttribute("group_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute group2attr3 = new PerunAttribute("group_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute group2attr4 = new PerunAttribute("group_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute group2attr5 = new PerunAttribute("group_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute group2attr6 = new PerunAttribute("group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute group2attr7 = new PerunAttribute("group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_attr_str", group2attr1);
		attributes.put("group_attr_int", group2attr2);
		attributes.put("group_attr_bool", group2attr3);
		attributes.put("group_attr_array", group2attr4);
		attributes.put("group_attr_map", group2attr5);
		attributes.put("group_attr_lstring", group2attr6);
		attributes.put("group_attr_larray", group2attr7);

		Integer id = 2;
		String name = "group2";
		String description = "dsc2";
		Integer voId = 2;
		Integer parentGroupId = null;

		EXPECTED2 = new Group(id, name, description, voId, parentGroupId, attributes, null);
	}

	private void setUpGroup23() {
		PerunAttribute group23attr1 = new PerunAttribute("group_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute group23attr2 = new PerunAttribute("group_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute group23attr3 = new PerunAttribute("group_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute group23attr4 = new PerunAttribute("group_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute group23attr5 = new PerunAttribute("group_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute group23attr6 = new PerunAttribute("group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute group23attr7 = new PerunAttribute("group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_attr_str", group23attr1);
		attributes.put("group_attr_int", group23attr2);
		attributes.put("group_attr_bool", group23attr3);
		attributes.put("group_attr_array", group23attr4);
		attributes.put("group_attr_map", group23attr5);
		attributes.put("group_attr_lstring", group23attr6);
		attributes.put("group_attr_larray", group23attr7);

		Integer id = 23;
		String name = "group23";
		String description = "dsc23";
		Integer voId = 2;
		Integer parentGroupId = 2;

		EXPECTED23 = new Group(id, name, description, voId, parentGroupId, attributes, null);
	}

	private void setUpGroup123() {
		PerunAttribute group23attr1 = new PerunAttribute("group_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute group23attr2 = new PerunAttribute("group_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute group23attr3 = new PerunAttribute("group_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute group23attr4 = new PerunAttribute("group_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute group23attr5 = new PerunAttribute("group_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute group23attr6 = new PerunAttribute("group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute group23attr7 = new PerunAttribute("group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_attr_str", group23attr1);
		attributes.put("group_attr_int", group23attr2);
		attributes.put("group_attr_bool", group23attr3);
		attributes.put("group_attr_array", group23attr4);
		attributes.put("group_attr_map", group23attr5);
		attributes.put("group_attr_lstring", group23attr6);
		attributes.put("group_attr_larray", group23attr7);

		Integer id = 123;
		String name = "group123";
		String description = "dsc123";
		Integer voId = 23;
		Integer parentGroupId = 23;

		EXPECTED123 = new Group(id, name, description, voId, parentGroupId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findGroupByIdTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByNameTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"name\" : {\"value\": [\"group1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"description\" : {\"value\": [\"dsc1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByVoIdTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"voId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByParentGroupIdTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"parentGroupId\" : {\"value\": [2]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED23, result.get(0));
	}

	@Test
	public void findAllGroupsTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(4, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findGroupByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_map\", \"value\" : [{\"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"id\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"name\" : {\"value\": [\"group2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByDescriptionLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"description\" : {\"value\": [\"dsc2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByVoIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"voId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByParentGroupIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"parentGroupId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23));
	}

	@Test
	public void findGroupByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_str\", \"value\" : [\"value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_int\", \"value\" : [2], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_bool\", \"value\" : [false], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_map\", \"value\" : [{\"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_larray\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED123, EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupByGroupEntityTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": [23]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findGroupByVoEntityTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByExtSourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"ext_source\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByMemberEntityTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByGroupResourceRelationTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group_resource\", \"resourceId\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByExtMemberGroupRelationTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_group\", \"memberId\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

