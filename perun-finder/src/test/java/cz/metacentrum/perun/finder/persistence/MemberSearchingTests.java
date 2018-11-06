package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.GeneralSearcherDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Member;
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
public class MemberSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static GeneralSearcherManager generalSearcherManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Member EXPECTED1;
	private Member EXPECTED2;
	private Member EXPECTED23;

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
		setUpMember1();
		setUpMember2();
		setUpMember23();
	}

	private void setUpMember1() {
		PerunAttribute member1attr1 = new PerunAttribute("member_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute member1attr2 = new PerunAttribute("member_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute member1attr3 = new PerunAttribute("member_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute member1attr4 = new PerunAttribute("member_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute member1attr5 = new PerunAttribute("member_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute member1attr6 = new PerunAttribute("member_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute member1attr7 = new PerunAttribute("member_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_attr_str", member1attr1);
		attributes.put("member_attr_int", member1attr2);
		attributes.put("member_attr_bool", member1attr3);
		attributes.put("member_attr_array", member1attr4);
		attributes.put("member_attr_map", member1attr5);
		attributes.put("member_attr_lstring", member1attr6);
		attributes.put("member_attr_larray", member1attr7);

		Integer id = 1;
		Integer userId = 1;
		Integer voId = 1;
		boolean sponsored = true;

		EXPECTED1 = new Member(id, userId, voId, sponsored, attributes, null);
	}

	private void setUpMember2() {
		PerunAttribute member2attr1 = new PerunAttribute("member_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute member2attr2 = new PerunAttribute("member_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute member2attr3 = new PerunAttribute("member_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute member2attr4 = new PerunAttribute("member_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute member2attr5 = new PerunAttribute("member_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute member2attr6 = new PerunAttribute("member_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute member2attr7 = new PerunAttribute("member_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_attr_str", member2attr1);
		attributes.put("member_attr_int", member2attr2);
		attributes.put("member_attr_bool", member2attr3);
		attributes.put("member_attr_array", member2attr4);
		attributes.put("member_attr_map", member2attr5);
		attributes.put("member_attr_lstring", member2attr6);
		attributes.put("member_attr_larray", member2attr7);

		Integer id = 2;
		Integer userId = 2;
		Integer voId = 2;
		boolean sponsored = false;

		EXPECTED2 = new Member(id, userId, voId, sponsored, attributes, null);
	}

	private void setUpMember23() {
		PerunAttribute member23attr1 = new PerunAttribute("member_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute member23attr2 = new PerunAttribute("member_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute member23attr3 = new PerunAttribute("member_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute member23attr4 = new PerunAttribute("member_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute member23attr5 = new PerunAttribute("member_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute member23attr6 = new PerunAttribute("member_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute member23attr7 = new PerunAttribute("member_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_attr_str", member23attr1);
		attributes.put("member_attr_int", member23attr2);
		attributes.put("member_attr_bool", member23attr3);
		attributes.put("member_attr_array", member23attr4);
		attributes.put("member_attr_map", member23attr5);
		attributes.put("member_attr_lstring", member23attr6);
		attributes.put("member_attr_larray", member23attr7);

		Integer id = 23;
		Integer userId = 23;
		Integer voId = 23;
		boolean sponsored = false;

		EXPECTED23 = new Member(id, userId, voId, sponsored, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findMemberByIdTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByUserIdTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"userId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByVoIdTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"voId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberBySponsoredTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"sponsored\" : {\"value\": [true]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllMembersTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findMemberByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_map\", \"value\" : [{ \"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"id\" : {\"value\": [2], \"matchLike\":true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByUserIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"userId\" : {\"value\": [2], \"matchLike\":true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByVoIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"voId\" : {\"value\": [2], \"matchLike\":true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberBySponsoredLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"sponsored\" : {\"value\": [false], \"matchLike\":true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_str\", \"value\" : [\"value2\"], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_int\", \"value\" : [2], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_bool\", \"value\" : [false], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_array\", \"value\" : [[3,4]], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_map\", \"value\" : [{ \"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_larray\", \"value\" : [[3,4]], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberByVoEntityTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByUserEntityTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByGroupEntityTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByMemberResourceRelationTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_resource\", \"resourceId\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByMemberGroupRelationTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_group\", \"groupId\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

