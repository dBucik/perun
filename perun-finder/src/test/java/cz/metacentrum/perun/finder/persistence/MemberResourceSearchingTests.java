package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.FinderDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.relations.MemberResource;
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
public class MemberResourceSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static FinderManager finderManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private MemberResource EXPECTED1;
	private MemberResource EXPECTED2;
	private MemberResource EXPECTED23;

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
		setUpMemberResource1();
		setUpMemberResource2();
		setUpMemberResource23();
	}

	private void setUpMemberResource1() {
		PerunAttribute memberResource1attr1 = new PerunAttribute("member_resource_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute memberResource1attr2 = new PerunAttribute("member_resource_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute memberResource1attr3 = new PerunAttribute("member_resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute memberResource1attr4 = new PerunAttribute("member_resource_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute memberResource1attr5 = new PerunAttribute("member_resource_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute memberResource1attr6 = new PerunAttribute("member_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute memberResource1attr7 = new PerunAttribute("member_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_resource_attr_str", memberResource1attr1);
		attributes.put("member_resource_attr_int", memberResource1attr2);
		attributes.put("member_resource_attr_bool", memberResource1attr3);
		attributes.put("member_resource_attr_array", memberResource1attr4);
		attributes.put("member_resource_attr_map", memberResource1attr5);
		attributes.put("member_resource_attr_lstring", memberResource1attr6);
		attributes.put("member_resource_attr_larray", memberResource1attr7);

		Integer memberId = 1;
		Integer resourceId = 1;

		EXPECTED1 = new MemberResource(memberId, resourceId, attributes, null);
	}

	private void setUpMemberResource2() {
		PerunAttribute memberResource2attr1 = new PerunAttribute("member_resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute memberResource2attr2 = new PerunAttribute("member_resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute memberResource2attr3 = new PerunAttribute("member_resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute memberResource2attr4 = new PerunAttribute("member_resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute memberResource2attr5 = new PerunAttribute("member_resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute memberResource2attr6 = new PerunAttribute("member_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute memberResource2attr7 = new PerunAttribute("member_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_resource_attr_str", memberResource2attr1);
		attributes.put("member_resource_attr_int", memberResource2attr2);
		attributes.put("member_resource_attr_bool", memberResource2attr3);
		attributes.put("member_resource_attr_array", memberResource2attr4);
		attributes.put("member_resource_attr_map", memberResource2attr5);
		attributes.put("member_resource_attr_lstring", memberResource2attr6);
		attributes.put("member_resource_attr_larray", memberResource2attr7);

		Integer memberId = 2;
		Integer resourceId = 2;

		EXPECTED2 = new MemberResource(memberId, resourceId, attributes, null);
	}

	private void setUpMemberResource23() {
		PerunAttribute memberResource23attr1 = new PerunAttribute("member_resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute memberResource23attr2 = new PerunAttribute("member_resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute memberResource23attr3 = new PerunAttribute("member_resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute memberResource23attr4 = new PerunAttribute("member_resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute memberResource23attr5 = new PerunAttribute("member_resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute memberResource23attr6 = new PerunAttribute("member_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute memberResource23attr7 = new PerunAttribute("member_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_resource_attr_str", memberResource23attr1);
		attributes.put("member_resource_attr_int", memberResource23attr2);
		attributes.put("member_resource_attr_bool", memberResource23attr3);
		attributes.put("member_resource_attr_array", memberResource23attr4);
		attributes.put("member_resource_attr_map", memberResource23attr5);
		attributes.put("member_resource_attr_lstring", memberResource23attr6);
		attributes.put("member_resource_attr_larray", memberResource23attr7);

		Integer memberId = 23;
		Integer resourceId = 23;

		EXPECTED23 = new MemberResource(memberId, resourceId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findMemberResourceRelationByMemberIdTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"memberId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByResourceIdTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"resourceId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllMemberResourceRelationsTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findMemberResourceRelationByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [{ \"name\" : \"member_resource_attr_map\", \"value\" : [{ \"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByMemberIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"memberId\" : {\"value\": [2], \"matchLike\":true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByResourceIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"resourceId\" : {\"value\": [2], \"matchLike\":true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_str\", \"value\" : [\"value2\"], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_int\", \"value\" : [2], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_bool\", \"value\" : [false], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_array\", \"value\" : [[3,4]], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_map\", \"value\" : [{ \"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_larray\", \"value\" : [[3,4]], \"matchLike\":true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findMemberResourceRelationByMemberEntityTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

