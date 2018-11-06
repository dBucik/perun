package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.GeneralSearcherDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Vo;
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
public class VoSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static GeneralSearcherManager generalSearcherManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Vo EXPECTED1;
	private Vo EXPECTED2;
	private Vo EXPECTED23;

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
		setUpVo1();
		setUpVo2();
		setUpVo23();
	}

	private void setUpVo1() {
		PerunAttribute vo1attr1 = new PerunAttribute("vo_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute vo1attr2 = new PerunAttribute("vo_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute vo1attr3 = new PerunAttribute("vo_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute vo1attr4 = new PerunAttribute("vo_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute vo1attr5 = new PerunAttribute("vo_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute vo1attr6 = new PerunAttribute("vo_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute vo1attr7 = new PerunAttribute("vo_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("vo_attr_str", vo1attr1);
		attributes.put("vo_attr_int", vo1attr2);
		attributes.put("vo_attr_bool", vo1attr3);
		attributes.put("vo_attr_array", vo1attr4);
		attributes.put("vo_attr_map", vo1attr5);
		attributes.put("vo_attr_lstring", vo1attr6);
		attributes.put("vo_attr_larray", vo1attr7);

		Integer id = 1;
		String shortName = "vo1";
		String name = "virtual_organization1";

		EXPECTED1 = new Vo(id, name, shortName, attributes, null);
	}

	private void setUpVo2() {
		PerunAttribute vo2attr1 = new PerunAttribute("vo_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute vo2attr2 = new PerunAttribute("vo_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute vo2attr3 = new PerunAttribute("vo_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute vo2attr4 = new PerunAttribute("vo_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute vo2attr5 = new PerunAttribute("vo_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute vo2attr6 = new PerunAttribute("vo_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute vo2attr7 = new PerunAttribute("vo_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("vo_attr_str", vo2attr1);
		attributes.put("vo_attr_int", vo2attr2);
		attributes.put("vo_attr_bool", vo2attr3);
		attributes.put("vo_attr_array", vo2attr4);
		attributes.put("vo_attr_map", vo2attr5);
		attributes.put("vo_attr_lstring", vo2attr6);
		attributes.put("vo_attr_larray", vo2attr7);

		Integer id = 2;
		String shortName = "vo2";
		String name = "virtual_organization2";

		EXPECTED2 = new Vo(id, name, shortName, attributes, null);
	}

	private void setUpVo23() {
		PerunAttribute vo23attr1 = new PerunAttribute("vo_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute vo23attr2 = new PerunAttribute("vo_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute vo23attr3 = new PerunAttribute("vo_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute vo23attr4 = new PerunAttribute("vo_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute vo23attr5 = new PerunAttribute("vo_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute vo23attr6 = new PerunAttribute("vo_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute vo23attr7 = new PerunAttribute("vo_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("vo_attr_str", vo23attr1);
		attributes.put("vo_attr_int", vo23attr2);
		attributes.put("vo_attr_bool", vo23attr3);
		attributes.put("vo_attr_array", vo23attr4);
		attributes.put("vo_attr_map", vo23attr5);
		attributes.put("vo_attr_lstring", vo23attr6);
		attributes.put("vo_attr_larray", vo23attr7);

		Integer id = 23;
		String shortName = "vo23";
		String name = "virtual_organization23";

		EXPECTED23 = new Vo(id, name, shortName, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findVoByIdTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByNameTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"name\" : {\"value\": [\"virtual_organization1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByShortNameTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"shortName\" : {\"value\": [\"vo1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllVosTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findVoByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_map\", \"value\" : [{ \"key1\":\"value1\", \"key2\":\"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"id\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"name\" : {\"value\":[\"virtual_organization2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByShortNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"shortName\" : {\"value\":[\"vo2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_str\", \"value\" : [\"value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_int\", \"value\" : [2], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_bool\", \"value\" : [false], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_map\", \"value\" : [{\"key3\":\"value3\", \"key4\":\"value4\"}], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_larray\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findVoByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByExtSourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"ext_source\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByGroupEntityTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByMemberEntityTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : {\"value\": [1] } }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

