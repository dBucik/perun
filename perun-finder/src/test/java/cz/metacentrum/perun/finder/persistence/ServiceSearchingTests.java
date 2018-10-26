package cz.metacentrum.perun.finder.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perun.finder.DBUtils;
import cz.metacentrum.perun.finder.persistence.data.FinderDAOImpl;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.entities.basic.Service;
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
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class ServiceSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static FinderManager finderManager;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Service EXPECTED1;
	private Service EXPECTED2;
	private Service EXPECTED23;

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
		setUpService1();
		setUpService2();
		setUpService23();
	}

	private void setUpService1() {
		Integer id = 1;
		String name = "service1";
		String description = "dsc1";
		int delay = 1;
		int recurrence = 1;
		boolean enabled = true;
		String script = "script1";

		EXPECTED1 = new Service(id, name, description, delay, recurrence, enabled, script, null);
	}

	private void setUpService2() {
		Integer id = 2;
		String name = "service2";
		String description = "dsc2";
		int delay = 2;
		int recurrence = 2;
		boolean enabled = false;
		String script = "script2";

		EXPECTED2 = new Service(id, name, description, delay, recurrence, enabled, script, null);
	}

	private void setUpService23() {
		Integer id = 23;
		String name = "service23";
		String description = "dsc23";
		int delay = 23;
		int recurrence = 23;
		boolean enabled = false;
		String script = "script23";

		EXPECTED23 = new Service(id, name, description, delay, recurrence, enabled, script, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findServiceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"id\" : {\"value\": [1]} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByNameTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"name\" : {\"value\": [\"service1\"]} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"description\" : {\"value\": [\"dsc1\"]} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByDelayTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"delay\" : {\"value\": [1]} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByRecurrenceTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"recurrence\" : {\"value\": [1]} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByEnabledTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"enabled\" : {\"value\": [true]} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByScriptTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"script\" : {\"value\": [\"script1\"]}}";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllServicesTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\"}";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findServiceByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"id\" : {\"value\": [2], \"matchLike\": true} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"name\" : {\"value\": [\"service2\"], \"matchLike\": true} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByDescriptionLikeTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"description\" : {\"value\": [\"dsc2\"], \"matchLike\": true} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByDelayLikeTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"delay\" : {\"value\": [2], \"matchLike\": true} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByRecurrenceLikeTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"recurrence\" : {\"value\": [2], \"matchLike\": true} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByEnabledLikeTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"enabled\" : {\"value\": [false], \"matchLike\": true} }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByScriptLikeTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"script\" : {\"value\": [\"script2\"], \"matchLike\": true}}";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"SERVICE\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = finderManager.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

