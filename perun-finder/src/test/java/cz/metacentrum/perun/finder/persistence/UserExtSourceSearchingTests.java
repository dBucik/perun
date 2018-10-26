package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.FinderDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.UserExtSource;
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
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class UserExtSourceSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static FinderManager finderManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private UserExtSource EXPECTED1;
	private UserExtSource EXPECTED2;
	private UserExtSource EXPECTED23;

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
		setUpUes1();
		setUpUes2();
		setUpUes23();
	}

	private void setUpUes1() {
		PerunAttribute userExtSource1attr1 = new PerunAttribute("user_ext_source_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute userExtSource1attr2 = new PerunAttribute("user_ext_source_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute userExtSource1attr3 = new PerunAttribute("user_ext_source_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute userExtSource1attr4 = new PerunAttribute("user_ext_source_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute userExtSource1attr5 = new PerunAttribute("user_ext_source_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute userExtSource1attr6 = new PerunAttribute("user_ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userExtSource1attr7 = new PerunAttribute("user_ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_ext_source_attr_str", userExtSource1attr1);
		attributes.put("user_ext_source_attr_int", userExtSource1attr2);
		attributes.put("user_ext_source_attr_bool", userExtSource1attr3);
		attributes.put("user_ext_source_attr_array", userExtSource1attr4);
		attributes.put("user_ext_source_attr_map", userExtSource1attr5);
		attributes.put("user_ext_source_attr_lstring", userExtSource1attr6);
		attributes.put("user_ext_source_attr_larray", userExtSource1attr7);

		Integer id = 1;
		Integer userId = 1;
		String loginExt = "login_ext1";
		Integer extSourcesId = 1;
		int loa = 0;
		Timestamp stamp = Timestamp.valueOf("2018-01-01 08:00:00");
		Long lastAccess = stamp.getTime();

		EXPECTED1 = new UserExtSource(id, userId, loginExt, extSourcesId, loa, lastAccess, attributes, null);
	}

	private void setUpUes2() {
		PerunAttribute userExtSource2attr1 = new PerunAttribute("user_ext_source_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute userExtSource2attr2 = new PerunAttribute("user_ext_source_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute userExtSource2attr3 = new PerunAttribute("user_ext_source_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute userExtSource2attr4 = new PerunAttribute("user_ext_source_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute userExtSource2attr5 = new PerunAttribute("user_ext_source_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute userExtSource2attr6 = new PerunAttribute("user_ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userExtSource2attr7 = new PerunAttribute("user_ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_ext_source_attr_str", userExtSource2attr1);
		attributes.put("user_ext_source_attr_int", userExtSource2attr2);
		attributes.put("user_ext_source_attr_bool", userExtSource2attr3);
		attributes.put("user_ext_source_attr_array", userExtSource2attr4);
		attributes.put("user_ext_source_attr_map", userExtSource2attr5);
		attributes.put("user_ext_source_attr_lstring", userExtSource2attr6);
		attributes.put("user_ext_source_attr_larray", userExtSource2attr7);

		Integer id = 2;
		Integer userId = 2;
		String loginExt = "login_ext2";
		Integer extSourcesId = 2;
		int loa = 1;
		Timestamp stamp = Timestamp.valueOf("2018-01-02 08:00:00");
		Long lastAccess = stamp.getTime();

		EXPECTED2 = new UserExtSource(id, userId, loginExt, extSourcesId, loa, lastAccess, attributes, null);
	}

	private void setUpUes23() {
		PerunAttribute userExtSource23attr1 = new PerunAttribute("user_ext_source_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute userExtSource23attr2 = new PerunAttribute("user_ext_source_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute userExtSource23attr3 = new PerunAttribute("user_ext_source_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute userExtSource23attr4 = new PerunAttribute("user_ext_source_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute userExtSource23attr5 = new PerunAttribute("user_ext_source_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute userExtSource23attr6 = new PerunAttribute("user_ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userExtSource23attr7 = new PerunAttribute("user_ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_ext_source_attr_str", userExtSource23attr1);
		attributes.put("user_ext_source_attr_int", userExtSource23attr2);
		attributes.put("user_ext_source_attr_bool", userExtSource23attr3);
		attributes.put("user_ext_source_attr_array", userExtSource23attr4);
		attributes.put("user_ext_source_attr_map", userExtSource23attr5);
		attributes.put("user_ext_source_attr_lstring", userExtSource23attr6);
		attributes.put("user_ext_source_attr_larray", userExtSource23attr7);

		Integer id = 23;
		Integer userId = 23;
		String loginExt = "login_ext23";
		Integer extSourcesId = 23;
		int loa = 1;
		Timestamp stamp = Timestamp.valueOf("2018-01-02 08:00:00");
		Long lastAccess = stamp.getTime();

		EXPECTED23 = new UserExtSource(id, userId, loginExt, extSourcesId, loa, lastAccess, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findUserExtSourceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByUserIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"userId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLoginExtTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"loginExt\" : {\"value\": [\"login_ext1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByExtSourcesIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"extSourcesId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLoaTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"loa\" : {\"value\": [0]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLastAccessTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"lastAccess\" : {\"value\": [\"2018-01-01 08:00:00\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllUserExtSourcesTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findUserExtSourceByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_map\", \"value\" : [{ \"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"id\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByUserIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"userId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByLoginExtLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"loginExt\" : {\"value\": [\"login_ext2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByExtSourcesIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"extSourcesId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByLoaLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"loa\" : {\"value\": [1], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByLastAccessLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"lastAccess\" : {\"value\": [\"2018-01-02 08:00:00\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_str\", \"value\" : [\"value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_int\", \"value\" : [2], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_bool\", \"value\" : [false], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_map\", \"value\" : [{ \"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_larray\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserExtSourceByUserEntityTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByExtSourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"ext_source\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

