package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.GeneralSearcherDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Resource;
import cz.metacentrum.perun.finder.service.GeneralSearcherManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
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
public class ResourceSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static GeneralSearcherManager generalSearcherManager;
	private static final org.springframework.core.io.Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final org.springframework.core.io.Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Resource EXPECTED1;
	private Resource EXPECTED2;
	private Resource EXPECTED23;

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
		setUpResource1();
		setUpResource2();
		setUpResource23();
	}

	private void setUpResource1() {
		PerunAttribute resource1attr1 = new PerunAttribute("resource_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute resource1attr2 = new PerunAttribute("resource_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute resource1attr3 = new PerunAttribute("resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute resource1attr4 = new PerunAttribute("resource_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute resource1attr5 = new PerunAttribute("resource_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute resource1attr6 = new PerunAttribute("resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute resource1attr7 = new PerunAttribute("resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("resource_attr_str", resource1attr1);
		attributes.put("resource_attr_int", resource1attr2);
		attributes.put("resource_attr_bool", resource1attr3);
		attributes.put("resource_attr_array", resource1attr4);
		attributes.put("resource_attr_map", resource1attr5);
		attributes.put("resource_attr_lstring", resource1attr6);
		attributes.put("resource_attr_larray", resource1attr7);

		Integer id = 1;
		Integer facilityId = 1;
		String name = "resource1";
		String dsc = "dsc1";
		Integer voId = 1;

		EXPECTED1 = new Resource(id, facilityId, name, dsc, voId, attributes, null);
	}

	private void setUpResource2() {
		PerunAttribute resource2attr1 = new PerunAttribute("resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute resource2attr2 = new PerunAttribute("resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute resource2attr3 = new PerunAttribute("resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute resource2attr4 = new PerunAttribute("resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute resource2attr5 = new PerunAttribute("resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute resource2attr6 = new PerunAttribute("resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute resource2attr7 = new PerunAttribute("resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("resource_attr_str", resource2attr1);
		attributes.put("resource_attr_int", resource2attr2);
		attributes.put("resource_attr_bool", resource2attr3);
		attributes.put("resource_attr_array", resource2attr4);
		attributes.put("resource_attr_map", resource2attr5);
		attributes.put("resource_attr_lstring", resource2attr6);
		attributes.put("resource_attr_larray", resource2attr7);

		Integer id = 2;
		Integer facilityId = 2;
		String name = "resource2";
		String dsc = "dsc2";
		Integer voId = 2;

		EXPECTED2 = new Resource(id, facilityId, name, dsc, voId, attributes, null);
	}

	private void setUpResource23() {
		PerunAttribute resource23attr1 = new PerunAttribute("resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute resource23attr2 = new PerunAttribute("resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute resource23attr3 = new PerunAttribute("resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute resource23attr4 = new PerunAttribute("resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute resource23attr5 = new PerunAttribute("resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute resource23attr6 = new PerunAttribute("resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute resource23attr7 = new PerunAttribute("resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("resource_attr_str", resource23attr1);
		attributes.put("resource_attr_int", resource23attr2);
		attributes.put("resource_attr_bool", resource23attr3);
		attributes.put("resource_attr_array", resource23attr4);
		attributes.put("resource_attr_map", resource23attr5);
		attributes.put("resource_attr_lstring", resource23attr6);
		attributes.put("resource_attr_larray", resource23attr7);

		Integer id = 23;
		Integer facilityId = 23;
		String name = "resource23";
		String dsc = "dsc23";
		Integer voId = 23;

		EXPECTED23 = new Resource(id, facilityId, name, dsc, voId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findResourceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByFacilityIdTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"facilityId\" : {\"value\": [1]} , \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByNameTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"name\" : {\"value\": [\"resource1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"description\" : {\"value\": [\"dsc1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByVoIdTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"voId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllResourcesTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findResourceByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_map\", \"value\" : [{ \"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"id\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByFacilityIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"facilityId\" : {\"value\": [2], \"matchLike\": true} , \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"name\" : {\"value\": [\"resource2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByDescriptionLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"description\" : {\"value\": [\"dsc2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByVoIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"voId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_str\", \"value\" : [\"value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_int\", \"value\" : [2], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_bool\", \"value\" : [false], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_map\", \"value\" : [{ \"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_larray\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findResourceByMemberEntityTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByVoEntityTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByServiceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"SERVICE\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByFacilityEntityTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"facility\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByGroupEntityTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByMemberResourceRelationTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_resource\", \"memberId\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByGroupResourceRelationTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group_resource\", \"groupId\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

