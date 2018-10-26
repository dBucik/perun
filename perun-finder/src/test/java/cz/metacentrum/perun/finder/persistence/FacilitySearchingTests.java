package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.FinderDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Facility;
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
public class FacilitySearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static FinderManager finderManager;

	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Facility EXPECTED1;
	private Facility EXPECTED2;
	private Facility EXPECTED23;

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
		setUpFacility1();
		setUpFacility2();
		setUpFacility23();
	}

	private void setUpFacility1() {
		PerunAttribute facility1attr1 = new PerunAttribute("facility_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute facility1attr2 = new PerunAttribute("facility_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute facility1attr3 = new PerunAttribute("facility_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute facility1attr4 = new PerunAttribute("facility_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute facility1attr5 = new PerunAttribute("facility_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute facility1attr6 = new PerunAttribute("facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute facility1attr7 = new PerunAttribute("facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("facility_attr_str", facility1attr1);
		attributes.put("facility_attr_int", facility1attr2);
		attributes.put("facility_attr_bool", facility1attr3);
		attributes.put("facility_attr_array", facility1attr4);
		attributes.put("facility_attr_map", facility1attr5);
		attributes.put("facility_attr_lstring", facility1attr6);
		attributes.put("facility_attr_larray", facility1attr7);

		Integer id = 1;
		String name = "facility1";
		String description = "dsc1";

		EXPECTED1 = new Facility(id, name, description, attributes, null);
	}

	private void setUpFacility2() {
		PerunAttribute facility2attr1 = new PerunAttribute("facility_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute facility2attr2 = new PerunAttribute("facility_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute facility2attr3 = new PerunAttribute("facility_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute facility2attr4 = new PerunAttribute("facility_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute facility2attr5 = new PerunAttribute("facility_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute facility2attr6 = new PerunAttribute("facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute facility2attr7 = new PerunAttribute("facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("facility_attr_str", facility2attr1);
		attributes.put("facility_attr_int", facility2attr2);
		attributes.put("facility_attr_bool", facility2attr3);
		attributes.put("facility_attr_array", facility2attr4);
		attributes.put("facility_attr_map", facility2attr5);
		attributes.put("facility_attr_lstring", facility2attr6);
		attributes.put("facility_attr_larray", facility2attr7);

		Integer id = 2;
		String name = "facility2";
		String description = "dsc2";

		EXPECTED2 = new Facility(id, name, description, attributes, null);
	}

	private void setUpFacility23() {
		PerunAttribute facility23attr1 = new PerunAttribute("facility_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute facility23attr2 = new PerunAttribute("facility_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute facility23attr3 = new PerunAttribute("facility_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute facility23attr4 = new PerunAttribute("facility_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute facility23attr5 = new PerunAttribute("facility_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute facility23attr6 = new PerunAttribute("facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute facility23attr7 = new PerunAttribute("facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("facility_attr_str", facility23attr1);
		attributes.put("facility_attr_int", facility23attr2);
		attributes.put("facility_attr_bool", facility23attr3);
		attributes.put("facility_attr_array", facility23attr4);
		attributes.put("facility_attr_map", facility23attr5);
		attributes.put("facility_attr_lstring", facility23attr6);
		attributes.put("facility_attr_larray", facility23attr7);

		Integer id = 23;
		String name = "facility23";
		String description = "dsc23";

		EXPECTED23 = new Facility(id, name, description, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findFacilityByIdTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByNameTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"name\" : {\"value\": [\"facility1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"description\" : {\"value\": [\"dsc1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllFacilitiesTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findFacilityByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_map\", \"value\" : [{\"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"id\" : {\"value\": [2], \"matchLike\" : true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"name\" : {\"value\": [\"facility2\"], \"matchLike\" : true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByDescriptionLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"description\" : {\"value\": [\"dsc2\"], \"matchLike\" : true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_str\", \"value\" : [\"value2\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_int\", \"value\" : [2], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_bool\", \"value\" : [false], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_array\", \"value\" : [[3,4]], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_map\", \"value\" : [{ \"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_larray\", \"value\" : [[3,4]], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findFacilityByUserEntityTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByHostEntityTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"host\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByUserFacilityRelationTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user_facility\", \"userId\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

}

