package cz.metacentrum.perun.finder.service;

import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.ResourceInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.ServiceInput;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonToInputParserParsingServiceInputTest {

	private String input;
	private List<InputAttribute> core;
	private List<InputEntity> innerInputs;

	@Before
	public void setUp() throws Exception {
		setUpInputString();
		setUpCoreAttributes();
		setUpInnerInputs();
	}

	private void setUpInputString() {
		this.input = "{\n" +
				"  \"entityName\": \"SERVICE\",\n" +
				"  \"id\": {\"value\": [1]},\n" +
				"  \"name\": {\"value\": [\"service1\"]},\n" +
				"  \"description\": {\"value\": [\"description1\"]},\n" +
				"  \"delay\": {\"value\": [1]},\n" +
				"  \"recurrence\": {\"value\": [1]},\n" +
				"  \"enabled\": {\"value\": [true]},\n" +
				"  \"script\": {\"value\": [\"script1\"]},\n" +
				"  \"relations\": [\n" +
				"    {\n" +
				"      \"entityName\": \"RESOURCE\"" +
				"    }\n" +
				"  ]\n" +
				"}";
	}

	private void setUpCoreAttributes() throws AttributeTypeException {
		List<InputAttribute> core = new ArrayList<>();
		JSONArray val1 = new JSONArray();
		val1.put(1);
		JSONArray val2 = new JSONArray();
		val2.put("service1");
		JSONArray val3 = new JSONArray();
		val3.put("description1");
		JSONArray val4 = new JSONArray();
		val4.put(1);
		JSONArray val5 = new JSONArray();
		val5.put(1);
		JSONArray val6 = new JSONArray();
		val6.put("1");
		JSONArray val7 = new JSONArray();
		val7.put("script1");
		core.add(new InputAttribute("id", false, val1));
		core.add(new InputAttribute("name", false, val2));
		core.add(new InputAttribute("description", false, val3));
		core.add(new InputAttribute("delay", false, val4));
		core.add(new InputAttribute("recurrence", false, val5));
		core.add(new InputAttribute("enabled", false, val6));
		core.add(new InputAttribute("script", false, val7));
		this.core = core;
	}

	private void setUpInnerInputs() throws Exception {
		List<InputAttribute> core = new ArrayList<>();
		List<InputAttribute> attributes = new ArrayList<>();
		List<String> attributeNames = new ArrayList<>();
		List<InputEntity> inputs = new ArrayList<>();

		ResourceInput resource = new ResourceInput(false, core, attributes, attributeNames, inputs);

		List<InputEntity> innerInputs = new ArrayList<>();
		innerInputs.add(resource);

		this.innerInputs = innerInputs;
	}

	@Test
	public void testServiceInputClass() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(entity instanceof ServiceInput);
	}

	@Test
	public void testServiceInputCore() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.core, entity.getCore()));
	}

	@Test
	public void testServiceInputInnerInputs() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.innerInputs, entity.getInnerInputs()));
	}

}