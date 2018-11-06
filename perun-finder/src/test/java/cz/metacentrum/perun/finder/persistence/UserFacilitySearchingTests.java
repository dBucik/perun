package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.GeneralSearcherDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.relations.UserFacility;
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
public class UserFacilitySearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static GeneralSearcherManager generalSearcherManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private UserFacility EXPECTED1;
	private UserFacility EXPECTED2;
	private UserFacility EXPECTED23;

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
		setUpUserFacility1();
		setUpUserFacility2();
		setUpUserFacility23();
	}

	private void setUpUserFacility1() {
		PerunAttribute userFacility1attr1 = new PerunAttribute("user_facility_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute userFacility1attr2 = new PerunAttribute("user_facility_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute userFacility1attr3 = new PerunAttribute("user_facility_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute userFacility1attr4 = new PerunAttribute("user_facility_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute userFacility1attr5 = new PerunAttribute("user_facility_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute userFacility1attr6 = new PerunAttribute("user_facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userFacility1attr7 = new PerunAttribute("user_facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_facility_attr_str", userFacility1attr1);
		attributes.put("user_facility_attr_int", userFacility1attr2);
		attributes.put("user_facility_attr_bool", userFacility1attr3);
		attributes.put("user_facility_attr_array", userFacility1attr4);
		attributes.put("user_facility_attr_map", userFacility1attr5);
		attributes.put("user_facility_attr_lstring", userFacility1attr6);
		attributes.put("user_facility_attr_larray", userFacility1attr7);

		Integer userId = 1;
		Integer facilityId = 1;

		EXPECTED1 = new UserFacility(userId, facilityId, attributes, null);
	}

	private void setUpUserFacility2() {
		PerunAttribute userFacility2attr1 = new PerunAttribute("user_facility_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute userFacility2attr2 = new PerunAttribute("user_facility_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute userFacility2attr3 = new PerunAttribute("user_facility_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute userFacility2attr4 = new PerunAttribute("user_facility_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute userFacility2attr5 = new PerunAttribute("user_facility_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute userFacility2attr6 = new PerunAttribute("user_facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userFacility2attr7 = new PerunAttribute("user_facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_facility_attr_str", userFacility2attr1);
		attributes.put("user_facility_attr_int", userFacility2attr2);
		attributes.put("user_facility_attr_bool", userFacility2attr3);
		attributes.put("user_facility_attr_array", userFacility2attr4);
		attributes.put("user_facility_attr_map", userFacility2attr5);
		attributes.put("user_facility_attr_lstring", userFacility2attr6);
		attributes.put("user_facility_attr_larray", userFacility2attr7);

		Integer userId = 2;
		Integer facilityId = 2;

		EXPECTED2 = new UserFacility(userId, facilityId, attributes, null);
	}

	private void setUpUserFacility23() {
		PerunAttribute userFacility23attr1 = new PerunAttribute("user_facility_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute userFacility23attr2 = new PerunAttribute("user_facility_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute userFacility23attr3 = new PerunAttribute("user_facility_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute userFacility23attr4 = new PerunAttribute("user_facility_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute userFacility23attr5 = new PerunAttribute("user_facility_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute userFacility23attr6 = new PerunAttribute("user_facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userFacility23attr7 = new PerunAttribute("user_facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_facility_attr_str", userFacility23attr1);
		attributes.put("user_facility_attr_int", userFacility23attr2);
		attributes.put("user_facility_attr_bool", userFacility23attr3);
		attributes.put("user_facility_attr_array", userFacility23attr4);
		attributes.put("user_facility_attr_map", userFacility23attr5);
		attributes.put("user_facility_attr_lstring", userFacility23attr6);
		attributes.put("user_facility_attr_larray", userFacility23attr7);

		Integer userId = 23;
		Integer facilityId = 23;

		EXPECTED23 = new UserFacility(userId, facilityId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findUserFacilityRelationByUserIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"userId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByFacilityIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"facilityId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllUserFacilityRelationsTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findUserFacilityRelationByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_map\", \"value\" : [{ \"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityRelationByUserIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"userId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByFacilityIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"facilityId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_str\", \"value\" : [\"value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_int\", \"value\" : [2], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_bool\", \"value\" : [false], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_map\", \"value\" : [{ \"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [ { \"name\" : \"user_facility_attr_larray\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserFacilityRelationByUserEntityTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserFacilityByFacilityEntityTest() throws Exception {
		String input = "{\"entityName\" : \"user_facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"facility\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

