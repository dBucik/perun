package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.GeneralSearcherDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.relations.GroupResource;
import cz.metacentrum.perun.finder.service.GeneralSearcherManager;
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
public class GroupResourceSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static GeneralSearcherManager generalSearcherManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private GroupResource EXPECTED1;
	private GroupResource EXPECTED2;
	private GroupResource EXPECTED23;

	@BeforeClass
	public static void setUpDatabase() throws Exception {
		DataSource ds = pg.getEmbeddedPostgres().getPostgresDatabase();
		DBUtils.setUpDatabaseTables(ds, tablesFile, dataFile);
		JdbcTemplate template = new JdbcTemplate(ds);
		GeneralSearcherDAOImpl dao = new GeneralSearcherDAOImpl();
		dao.setTemplate(template);
		generalSearcherManager = new GeneralSearcherManager(dao);
	}

	@Before
	public void setUp() {
		setUpGroupResource1();
		setUpGroupResource2();
		setUpGroupResource23();
	}

	private void setUpGroupResource1() {
		PerunAttribute groupResource1attr1 = new PerunAttribute("group_resource_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute groupResource1attr2 = new PerunAttribute("group_resource_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute groupResource1attr3 = new PerunAttribute("group_resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute groupResource1attr4 = new PerunAttribute("group_resource_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute groupResource1attr5 = new PerunAttribute("group_resource_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute groupResource1attr6 = new PerunAttribute("group_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute groupResource1attr7 = new PerunAttribute("group_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_resource_attr_str", groupResource1attr1);
		attributes.put("group_resource_attr_int", groupResource1attr2);
		attributes.put("group_resource_attr_bool", groupResource1attr3);
		attributes.put("group_resource_attr_array", groupResource1attr4);
		attributes.put("group_resource_attr_map", groupResource1attr5);
		attributes.put("group_resource_attr_lstring", groupResource1attr6);
		attributes.put("group_resource_attr_larray", groupResource1attr7);

		Integer groupId = 1;
		Integer resourceId = 1;

		EXPECTED1 = new GroupResource(groupId, resourceId, attributes, null);
	}

	private void setUpGroupResource2() {
		PerunAttribute groupResource2attr1 = new PerunAttribute("group_resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute groupResource2attr2 = new PerunAttribute("group_resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute groupResource2attr3 = new PerunAttribute("group_resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute groupResource2attr4 = new PerunAttribute("group_resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute groupResource2attr5 = new PerunAttribute("group_resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute groupResource2attr6 = new PerunAttribute("group_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute groupResource2attr7 = new PerunAttribute("group_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_resource_attr_str", groupResource2attr1);
		attributes.put("group_resource_attr_int", groupResource2attr2);
		attributes.put("group_resource_attr_bool", groupResource2attr3);
		attributes.put("group_resource_attr_array", groupResource2attr4);
		attributes.put("group_resource_attr_map", groupResource2attr5);
		attributes.put("group_resource_attr_lstring", groupResource2attr6);
		attributes.put("group_resource_attr_larray", groupResource2attr7);

		Integer groupId = 2;
		Integer resourceId = 2;

		EXPECTED2 = new GroupResource(groupId, resourceId, attributes, null);
	}

	private void setUpGroupResource23() {
		PerunAttribute groupResource23attr1 = new PerunAttribute("group_resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute groupResource23attr2 = new PerunAttribute("group_resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute groupResource23attr3 = new PerunAttribute("group_resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute groupResource23attr4 = new PerunAttribute("group_resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute groupResource23attr5 = new PerunAttribute("group_resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute groupResource23attr6 = new PerunAttribute("group_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute groupResource23attr7 = new PerunAttribute("group_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_resource_attr_str", groupResource23attr1);
		attributes.put("group_resource_attr_int", groupResource23attr2);
		attributes.put("group_resource_attr_bool", groupResource23attr3);
		attributes.put("group_resource_attr_array", groupResource23attr4);
		attributes.put("group_resource_attr_map", groupResource23attr5);
		attributes.put("group_resource_attr_lstring", groupResource23attr6);
		attributes.put("group_resource_attr_larray", groupResource23attr7);

		Integer groupId = 23;
		Integer resourceId = 23;

		EXPECTED23 = new GroupResource(groupId, resourceId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findGroupResourceRelationByGroupIdTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"groupId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByResourceIdTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"resourceId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllGroupResourceRelationsTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findGroupResourceRelationByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_map\", \"value\" : [{ \"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByGroupIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"groupId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByResourceIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"resourceId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_str\", \"value\" : [\"value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_int\", \"value\" : [2], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_bool\", \"value\" : [false], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_map\", \"value\" : [{\"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_larray\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findGroupResourceRelationByGroupEntityTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

