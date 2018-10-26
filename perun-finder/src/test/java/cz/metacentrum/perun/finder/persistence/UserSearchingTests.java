package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.FinderDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.User;
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
public class UserSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static FinderManager finderManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private User EXPECTED1;
	private User EXPECTED2;
	private User EXPECTED23;

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
		setUpUser1();
		setUpUser2();
		setUpUser23();
	}

	private void setUpUser1() {
		PerunAttribute user1attr1 = new PerunAttribute("user_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute user1attr2 = new PerunAttribute("user_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute user1attr3 = new PerunAttribute("user_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute user1attr4 = new PerunAttribute("user_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute user1attr5 = new PerunAttribute("user_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute user1attr6 = new PerunAttribute("user_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute user1attr7 = new PerunAttribute("user_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_attr_str", user1attr1);
		attributes.put("user_attr_int", user1attr2);
		attributes.put("user_attr_bool", user1attr3);
		attributes.put("user_attr_array", user1attr4);
		attributes.put("user_attr_map", user1attr5);
		attributes.put("user_attr_lstring", user1attr6);
		attributes.put("user_attr_larray", user1attr7);

		Integer id = 1;
		String firstName = "first_name1";
		String middleName = "middle_name1";
		String lastName = "last_name1";
		String title_before = "title_before1";
		String title_after = "title_after1";
		boolean service = true;
		boolean sponsored = true;

		EXPECTED1 = new User(id, firstName, middleName, lastName, title_before, title_after, service, sponsored, attributes, null);
	}

	private void setUpUser2() {
		PerunAttribute user2attr1 = new PerunAttribute("user_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute user2attr2 = new PerunAttribute("user_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute user2attr3 = new PerunAttribute("user_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute user2attr4 = new PerunAttribute("user_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute user2attr5 = new PerunAttribute("user_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute user2attr6 = new PerunAttribute("user_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute user2attr7 = new PerunAttribute("user_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_attr_str", user2attr1);
		attributes.put("user_attr_int", user2attr2);
		attributes.put("user_attr_bool", user2attr3);
		attributes.put("user_attr_array", user2attr4);
		attributes.put("user_attr_map", user2attr5);
		attributes.put("user_attr_lstring", user2attr6);
		attributes.put("user_attr_larray", user2attr7);

		Integer id = 2;
		String firstName = "first_name2";
		String middleName = "middle_name2";
		String lastName = "last_name2";
		String title_before = "title_before2";
		String title_after = "title_after2";
		boolean service = false;
		boolean sponsored = false;

		EXPECTED2 = new User(id, firstName, middleName, lastName, title_before, title_after, service, sponsored, attributes, null);
	}

	private void setUpUser23() {
		PerunAttribute user23attr1 = new PerunAttribute("user_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute user23attr2 = new PerunAttribute("user_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute user23attr3 = new PerunAttribute("user_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute user23attr4 = new PerunAttribute("user_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute user23attr5 = new PerunAttribute("user_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute user23attr6 = new PerunAttribute("user_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute user23attr7 = new PerunAttribute("user_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_attr_str", user23attr1);
		attributes.put("user_attr_int", user23attr2);
		attributes.put("user_attr_bool", user23attr3);
		attributes.put("user_attr_array", user23attr4);
		attributes.put("user_attr_map", user23attr5);
		attributes.put("user_attr_lstring", user23attr6);
		attributes.put("user_attr_larray", user23attr7);

		Integer id = 23;
		String firstName = "first_name23";
		String middleName = "middle_name23";
		String lastName = "last_name23";
		String title_before = "title_before23";
		String title_after = "title_after23";
		boolean service = false;
		boolean sponsored = false;

		EXPECTED23 = new User(id, firstName, middleName, lastName, title_before, title_after, service, sponsored, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findUserByIdTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"id\": {\"value\": [1]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByFirstNameTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"firstName\": {\"value\": [\"first_name1\"]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByMiddleNameTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"middleName\": {\"value\": [\"middle_name1\"]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByLastNameTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"lastName\": {\"value\": [\"last_name1\"]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByTitleBeforeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"titleBefore\": {\"value\": [\"title_before1\"]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByTitleAfterNameTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"titleAfter\": {\"value\": [\"title_after1\"]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByServiceTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"serviceAcc\": {\"value\": [true]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserBySponsoredTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"sponsoredAcc\": {\"value\": [true]}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllUsersTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findUserByStringAttributeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_str\", \"value\": [\"value1\"]}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_int\", \"value\": [1]}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_bool\", \"value\": [true]}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_array\", \"value\": [[1,2]]}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByMapAttributeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_map\", \"value\": [{ \"key1\": \"value1\", \"key2\": \"value2\"}]}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\": [{\"name\": \"user_attr_lstring\", \"value\": [\"long_value1\"]}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\" : [ { \"name\" : \"user_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByIdLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"id\": {\"value\": [2], \"matchLike\": true}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByFirstNameLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"firstName\": {\"value\": [\"first_name2\"], \"matchLike\": true}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByMiddleNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"middleName\" : {\"value\": [\"middle_name2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByLastNameLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"lastName\" : {\"value\": [\"last_name2\"], \"matchLike\": true}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByTitleBeforeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"titleBefore\": {\"value\": [\"title_before2\"], \"matchLike\": true}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByTitleAfterNameLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"titleAfter\": {\"value\": [\"title_after2\"], \"matchLike\": true}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByServiceLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"serviceAcc\": {\"value\": [false], \"matchLike\": true}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserBySponsoredLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"sponsoredAcc\": {\"value\": [false], \"matchLike\": true}, \"attributes\": [], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_str\", \"value\": [\"value2\"], \"matchLike\": true}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_int\", \"value\": [2], \"matchLike\": true}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_bool\", \"value\": [false], \"matchLike\": true}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_map\", \"value\": [{\"key3\": \"value3\", \"key4\": \"value4\"}], \"matchLike\": true}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_lstring\", \"value\": [\"long_value2\"], \"matchLike\": true}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [{\"name\": \"user_attr_larray\", \"value\": [[3,4]], \"matchLike\": true}], \"attributeNames\": [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findUserByFacilityEntityTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [], \"attributesNames\": [\"ALL\"], \"relations\": [" +
				"{\"entityName\" : \"facility\", \"id\": {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByMemberEntityTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [], \"attributesNames\": [\"ALL\"], \"relations\": [" +
				"{\"entityName\": \"member\", \"id\": {\"value\": [1]}}" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByUserExtSourceEntityTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [], \"attributesNames\": [\"ALL\"], \"relations\": [" +
				"{\"entityName\": \"user_ext_source\", \"id\": {\"value\": [1]}}" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByUserFacilityRelationTest() throws Exception {
		String input = "{\"entityName\": \"user\", \"attributes\": [], \"attributesNames\": [\"ALL\"], \"relations\": [" +
				"{\"entityName\" : \"user_facility\", \"facilityId\" : {\"value\": [1]}}" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

