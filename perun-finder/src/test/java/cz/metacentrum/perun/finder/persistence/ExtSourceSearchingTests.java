package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.GeneralSearcherDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.ExtSource;
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
public class ExtSourceSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static GeneralSearcherManager generalSearcherManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private ExtSource EXPECTED1;
	private ExtSource EXPECTED2;
	private ExtSource EXPECTED23;

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
		setUpExtSource1();
		setUpExtSource2();
		setUpExtSource23();
	}

	private void setUpExtSource1() {
		PerunAttribute extSource1attr1 = new PerunAttribute("ext_source_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute extSource1attr2 = new PerunAttribute("ext_source_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute extSource1attr3 = new PerunAttribute("ext_source_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute extSource1attr4 = new PerunAttribute("ext_source_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute extSource1attr5 = new PerunAttribute("ext_source_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute extSource1attr6 = new PerunAttribute("ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute extSource1attr7 = new PerunAttribute("ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("ext_source_attr_str", extSource1attr1);
		attributes.put("ext_source_attr_int", extSource1attr2);
		attributes.put("ext_source_attr_bool", extSource1attr3);
		attributes.put("ext_source_attr_array", extSource1attr4);
		attributes.put("ext_source_attr_map", extSource1attr5);
		attributes.put("ext_source_attr_lstring", extSource1attr6);
		attributes.put("ext_source_attr_larray", extSource1attr7);

		Integer id = 1;
		String name = "ext_source1";
		String type = "type1";

		EXPECTED1 = new ExtSource(id, name, type, attributes, null);
	}

	private void setUpExtSource2() {
		PerunAttribute extSource2attr1 = new PerunAttribute("ext_source_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute extSource2attr2 = new PerunAttribute("ext_source_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute extSource2attr3 = new PerunAttribute("ext_source_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute extSource2attr4 = new PerunAttribute("ext_source_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute extSource2attr5 = new PerunAttribute("ext_source_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute extSource2attr6 = new PerunAttribute("ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute extSource2attr7 = new PerunAttribute("ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("ext_source_attr_str", extSource2attr1);
		attributes.put("ext_source_attr_int", extSource2attr2);
		attributes.put("ext_source_attr_bool", extSource2attr3);
		attributes.put("ext_source_attr_array", extSource2attr4);
		attributes.put("ext_source_attr_map", extSource2attr5);
		attributes.put("ext_source_attr_lstring", extSource2attr6);
		attributes.put("ext_source_attr_larray", extSource2attr7);

		Integer id = 2;
		String name = "ext_source2";
		String type = "type2";

		EXPECTED2 = new ExtSource(id, name, type, attributes, null);
	}

	private void setUpExtSource23() {
		PerunAttribute extSource23attr1 = new PerunAttribute("ext_source_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute extSource23attr2 = new PerunAttribute("ext_source_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute extSource23attr3 = new PerunAttribute("ext_source_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute extSource23attr4 = new PerunAttribute("ext_source_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute extSource23attr5 = new PerunAttribute("ext_source_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute extSource23attr6 = new PerunAttribute("ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute extSource23attr7 = new PerunAttribute("ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("ext_source_attr_str", extSource23attr1);
		attributes.put("ext_source_attr_int", extSource23attr2);
		attributes.put("ext_source_attr_bool", extSource23attr3);
		attributes.put("ext_source_attr_array", extSource23attr4);
		attributes.put("ext_source_attr_map", extSource23attr5);
		attributes.put("ext_source_attr_lstring", extSource23attr6);
		attributes.put("ext_source_attr_larray", extSource23attr7);

		Integer id = 23;
		String name = "ext_source23";
		String type = "type23";

		EXPECTED23 = new ExtSource(id, name, type, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findExtSourceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByNameTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"name\" : {\"value\": [\"ext_source1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByTypeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"type\" : {\"value\": [\"type1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllFacilitiesTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findExtSourceByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_int\", \"value\" : [\"1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_bool\", \"value\" : [\"true\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_array\", \"value\" : [\"1,2\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_map\", \"value\" : [\"key1:value1,key2:value2\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByIntegerStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByIntegerArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_larray\", \"value\" : [\"1,2\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"id\" : {\"value\": [2], \"matchLike\" : true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"name\" : {\"value\": [\"ext_source2\"], \"matchLike\" : true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByTypeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"type\" : {\"value\": [\"type2\"], \"matchLike\" : true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByStringAttributeTestTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_str\", \"value\" : [\"value2\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_int\", \"value\" : [\"2\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_bool\", \"value\" : [\"false\"] , \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_array\", \"value\" : [\"3,4\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_map\", \"value\" : [\"key3:value3,key4:value4\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_larray\", \"value\" : [\"3,4\"], \"matchLike\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findExtSourceByVoEntityTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByGroupEntityTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByUserExtSourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user_ext_source\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = generalSearcherManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

