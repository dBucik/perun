package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.FinderDAOImpl;
import cz.metacentrum.perun.finder.persistence.enums.PerunAttributeType;
import cz.metacentrum.perun.finder.persistence.models.PerunAttribute;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Host;
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
public class HostSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static FinderManager finderManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Host EXPECTED1;
	private Host EXPECTED2;
	private Host EXPECTED23;

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
		setUpHost1();
		setUpHost2();
		setUpHost23();
	}

	private void setUpHost1() {
		PerunAttribute host1attr1 = new PerunAttribute("host_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute host1attr2 = new PerunAttribute("host_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute host1attr3 = new PerunAttribute("host_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute host1attr4 = new PerunAttribute("host_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute host1attr5 = new PerunAttribute("host_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute host1attr6 = new PerunAttribute("host_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute host1attr7 = new PerunAttribute("host_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("host_attr_str", host1attr1);
		attributes.put("host_attr_int", host1attr2);
		attributes.put("host_attr_bool", host1attr3);
		attributes.put("host_attr_array", host1attr4);
		attributes.put("host_attr_map", host1attr5);
		attributes.put("host_attr_lstring", host1attr6);
		attributes.put("host_attr_larray", host1attr7);

		Integer id = 1;
		String hostname = "hostname1";
		Integer facilityId = 1;
		String dsc = "dsc1";

		EXPECTED1 = new Host(id, hostname, facilityId, dsc, attributes, null);
	}

	private void setUpHost2() {
		PerunAttribute host2attr1 = new PerunAttribute("host_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute host2attr2 = new PerunAttribute("host_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute host2attr3 = new PerunAttribute("host_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute host2attr4 = new PerunAttribute("host_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute host2attr5 = new PerunAttribute("host_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute host2attr6 = new PerunAttribute("host_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute host2attr7 = new PerunAttribute("host_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("host_attr_str", host2attr1);
		attributes.put("host_attr_int", host2attr2);
		attributes.put("host_attr_bool", host2attr3);
		attributes.put("host_attr_array", host2attr4);
		attributes.put("host_attr_map", host2attr5);
		attributes.put("host_attr_lstring", host2attr6);
		attributes.put("host_attr_larray", host2attr7);

		Integer id = 2;
		String hostname = "hostname2";
		Integer facilityId = 2;
		String dsc = "dsc2";

		EXPECTED2 = new Host(id, hostname, facilityId, dsc, attributes, null);
	}

	private void setUpHost23() {
		PerunAttribute host23attr1 = new PerunAttribute("host_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute host23attr2 = new PerunAttribute("host_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute host23attr3 = new PerunAttribute("host_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute host23attr4 = new PerunAttribute("host_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute host23attr5 = new PerunAttribute("host_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute host23attr6 = new PerunAttribute("host_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute host23attr7 = new PerunAttribute("host_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("host_attr_str", host23attr1);
		attributes.put("host_attr_int", host23attr2);
		attributes.put("host_attr_bool", host23attr3);
		attributes.put("host_attr_array", host23attr4);
		attributes.put("host_attr_map", host23attr5);
		attributes.put("host_attr_lstring", host23attr6);
		attributes.put("host_attr_larray", host23attr7);

		Integer id = 23;
		String hostname = "hostname23";
		Integer facilityId = 23;
		String dsc = "dsc23";


		EXPECTED23 = new Host(id, hostname, facilityId, dsc, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findHostByIdTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"id\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByHostnameTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"hostname\" : {\"value\": [\"hostname1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByFacilityIdTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"facilityId\" : {\"value\": [1]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"description\" : {\"value\": [\"dsc1\"]}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllHostsTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findHostByStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_str\", \"value\" : [\"value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByIntegerAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_int\", \"value\" : [1]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByBooleanAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_bool\", \"value\" : [true]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_array\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByMapAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_map\", \"value\" : [{ \"key1\" : \"value1\", \"key2\" : \"value2\"}]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByIntegerStringAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_lstring\", \"value\" : [\"long_value1\"]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByIntegerArrayAttributeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_larray\", \"value\" : [[1,2]]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"id\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByHostnameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"hostname\" : {\"value\": [\"hostname2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByFacilityIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"facilityId\" : {\"value\": [2], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByDescriptionLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"description\" : {\"value\": [\"dsc2\"], \"matchLike\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_str\", \"value\" : [\"value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByIntegerAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_int\", \"value\" : [2], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByBooleanAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_bool\", \"value\" : [false], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_array\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByMapAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_map\", \"value\" : [{ \"key3\" : \"value3\", \"key4\" : \"value4\"}], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByIntegerStringAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_lstring\", \"value\" : [\"long_value2\"], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByIntegerArrayAttributeLikeTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_larray\", \"value\" : [[3,4]], \"matchLike\": true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findHostByFacilityEntityTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"facility\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

}

